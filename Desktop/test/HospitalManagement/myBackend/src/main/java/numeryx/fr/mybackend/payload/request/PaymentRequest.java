package numeryx.fr.mybackend.payload.request;

import numeryx.fr.mybackend.entities.enums.OrderStatus;
import numeryx.fr.mybackend.payload.dto.payment.PayerDTO;
import numeryx.fr.mybackend.payload.dto.payment.PurchaseUnits;

import java.util.Date;
import java.util.List;

public class PaymentRequest {
	private Long idPerson;
	private String orderId;
	private Date createTime;
	private Date updateTime;
	private List<PurchaseUnits> purchaseUnits;
	private OrderStatus status;
	private PayerDTO payer;
	public PayerDTO getPayer() {
		return payer;
	}
	public void setPayer(PayerDTO payer) {
		this.payer = payer;
	}
	public Long getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<PurchaseUnits> getPurchaseUnits() {
		return purchaseUnits;
	}
	public void setPurchaseUnits(List<PurchaseUnits> purchaseUnits) {
		this.purchaseUnits = purchaseUnits;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PaymentRequest [idPerson=" + idPerson + ", orderId=" + orderId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", purchaseUnits=" + purchaseUnits + ", status=" + status + ", payer="
				+ payer + "]";
	}
	
	
}
