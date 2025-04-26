package numeryx.fr.mybackend.controllers;

import numeryx.fr.mybackend.config.jwt.JwtUtils;
import numeryx.fr.mybackend.dao.IRoleDao;
import numeryx.fr.mybackend.dao.IUserDao;
import numeryx.fr.mybackend.entities.*;
import numeryx.fr.mybackend.entities.enums.ERole;
import numeryx.fr.mybackend.entities.enums.EType;
import numeryx.fr.mybackend.payload.request.LoginRequest;
import numeryx.fr.mybackend.payload.request.RequestChangePassword;
import numeryx.fr.mybackend.payload.request.SignupRequest;
import numeryx.fr.mybackend.payload.response.JwtResponse;
import numeryx.fr.mybackend.payload.response.MessageResponse;
import numeryx.fr.mybackend.services.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	Logger logger = Logger.getLogger(AuthController.class.getName());

	@Autowired
	private ConfirmationTokenService IconfirmationToken;

	@Autowired
	private EmailService mailSender;

	@Autowired
	private UserServices services;

	@Autowired
	private RoleServices roleServices;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private DoctorServices serviceDoctor;

	@Autowired
	private PatientsServices servicesPatients;
	@PostMapping("/auth/signup")
	public ResponseEntity<?> registration(@RequestBody SignupRequest signUpRequest, Errors errors) {

		logger.log(Level.INFO, signUpRequest.toString());

		if (userDao.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userDao.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}

		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(new MessageResponse(errors));
		}

		// 🔥 Correction importante : vérifier si person est null
		if (signUpRequest.getPerson() == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Person information is required!"));
		}

		User user = new User();
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setPerson(signUpRequest.getPerson());
		user.getPerson().setEnable(true);
		user.setEnable(true);

		Integer age = Period.between(user.getPerson().getDob(), LocalDate.now()).getYears();
		user.getPerson().setAge(age);

		EType strType = signUpRequest.getPerson().getType();

		if (strType == null) {
			strType = EType.USER; // 🔥 Correction : par défaut, type = USER si absent
			signUpRequest.getPerson().setType(strType);
		}

		if (strType == EType.PATIENT) {
			Patient patient = new Patient();
			patient.setPerson(signUpRequest.getPerson());
			servicesPatients.save(patient);
		} else if (strType == EType.DOCTOR) {
			Doctor doctor = new Doctor();
			doctor.setPerson(signUpRequest.getPerson());
			serviceDoctor.save(doctor);
		}
		// Pas besoin de faire quoi que ce soit de spécial pour USER.

		// Gestion des rôles
		Set<Role> roles = new HashSet<>();
		if (signUpRequest.getRoles() != null && !signUpRequest.getRoles().isEmpty()) {
			Set<Role> strRoles = roleServices.getRolesByIdIn(signUpRequest.getRoles());
			strRoles.forEach(role -> {
				switch (role.getName()) {
					case ROLE_ADMIN:
						Role adminRole = roleDao.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					default:
						Role userRole = roleDao.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		} else {
			Role userRole = roleDao.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}

		user.setRoles(roles);
		user.setImage("https://cdn-icons-png.flaticon.com/512/2102/2102647.png");
		userDao.save(user);

		return ResponseEntity.ok(user);
	}


	@PostMapping("/auth/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
			throws UnsupportedEncodingException, MessagingException {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);

		CustomnUserDetails userDetails = (CustomnUserDetails) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		User user = userDao.findByUsername(loginRequest.getUsername()).get();

		if (!user.isStatus() && IconfirmationToken.findByUser(user).isEmpty()) {
			ConfirmationToken confirmToken = IconfirmationToken.createConfirmationToken(user.getId());
			mailSender.sendEmailConfirmation(user.getEmail(), confirmToken.getConfirmationToken());
		}
		if (!user.isStatus()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Account not verified"));
		}
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles, user.getPerson().getType().name(), user.isEnable()));
	}

	@PostMapping("/auth/verify/{token}")
	public ResponseEntity<?> confirmUserAccount(@Valid @PathVariable String token) throws Exception {
		ConfirmationToken tokenConfirmed = IconfirmationToken.findByConfirmationToken(token).get();

		if (tokenConfirmed != null) {
			User user = userDao.findByEmail(tokenConfirmed.getUser().getEmail());
			user.getPerson().setStatus(true);
			user.setStatus(true);
			services.save(user);
			tokenConfirmed.setStatus("VERIFIED");
			IconfirmationToken.save(tokenConfirmed);
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Account Verified"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse("The link is invalid or broken!"));
	}

	@GetMapping("/auth/refreshtoken/{token}")
	public ResponseEntity<?> refreshtoken(@Valid @PathVariable String token) {

		boolean auth = jwtUtils.validateJwtToken(token);
		if (auth) {
			return ResponseEntity.ok().body(true);
		}
		return ResponseEntity.badRequest().body(false);
	}

	@PostMapping("/auth/changepassword")
	public ResponseEntity<?> changePassword(@Valid @RequestBody RequestChangePassword request) throws Exception {

		User user = services.findById(request.getUserId());
		boolean check = false;

		if (user.getId() != null) {
			check = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
		}

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));

		if (check) {
			services.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Password changed"));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Incorrect password"));
	}

}
