<template>
	<div id="wire-money">
		<form action="">
			<h2>Wire Transfer Form</h2>
			<br />
			<label for="from-id">From Account ID: {{ account.accountId }}</label>
			<br />
			<label for="to-id">To Account ID: </label>
			<br />
			<label for="amount">Amount to Send: {{}}</label>
			<input
				type="text"
				name="amount"
				required
				v-model.number="transfer.amount"
				placeholder="100.00"
			/>
			<br />
			<button type="submit" @click.prevent="wireMoney(transfer)">
				Send Money
			</button>
			<button type="reset">Clear Form</button>
		</form>
	</div>
</template>

<script>
	import accountService from "@/services/AccountService";
	import transferService from "@/services/TransferService";
	export default {
		data() {
			return {
				account: {},
				transfer: {},
				from: this.$store.state.user.id,
				to: this.$route.params.id,
			};
		},
		created() {
			this.getAcctIdByUserId(this.from);
			// this.getAcctIdByUserId(this.account2.userId);
		},
		methods: {
			getAcctIdByUserId(userId) {
				accountService.getAccountByUserId(userId).then((response) => {
					this.account = response.data;
				});
			},
			wireMoney() {
				transferService.postTransfer(this.transfer).then((response) => {
					if (response.status === 201) {
						this.transfer = response.data;
						alert("Transfer complete!");
					}
				});
			},
		},
		computed: {},
	};
</script>

<style scoped></style>
