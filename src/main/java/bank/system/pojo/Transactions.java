package bank.system.pojo;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Transaction")
public class Transactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private int id;

	@Column(name = "account_id")
	private int accountId;

	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "amount")
	private double amount;

	@Column(name = "transaction_date")
	private Date transactionDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "Transactions [id=" + id + ", accountId=" + accountId + ", transactionType=" + transactionType
				+ ", amount=" + amount + ", transactionDate=" + transactionDate + "]";
	}

}
