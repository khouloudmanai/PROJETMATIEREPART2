const express = require("express");
const app = express();
app.use(express.json())
const users = [
    { name: "khouloud", age: 25 },
    { name: "med", age: 30 },

  ];
  
  app.post('/api/users/create', (req, res) => {
   users.push(req.body)
   res.send(users)
  })
  app.put('/api/users/update/:id', (req, res) => {
    const id = Number(req.params.id);
    users = users.map(user => user.id === id ? { ...user, ...req.body } : user);
    res.json(users);
});

// Supprimer un utilisateur
app.delete('/api/users/delete/:id', (req, res) => {
  const id = Number(req.params.id);
  const initialLength = users.length;
  
  users = users.filter(user => user.id !== id);

  if (users.length === initialLength) {
      return res.status(404).json({ message: "Utilisateur non trouvé" });
  }

  res.json({ message: "Utilisateur supprimé avec succès", users });
});

   app.get('/',(req,res)=>{
    res.send({message:"hello from my server"})//can return all types of data in reponse
res.json({message:"hello"}) //accept response only json
res.end({message:"do this and skip others requests"})
res.statusCode(404).send({message:"product not found"})
})
app.get('/file',(req,res)=>{
    res.sendFile(__dirname + '/public/index.html')
})

app.listen(4000,()=>

    
console.log('Listening on port '+ 4000));