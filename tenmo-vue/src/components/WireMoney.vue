<template>
	<div id="wire-money">
		<div class="status-message error" v-show="errorMsg !== ''">
			{{ errorMsg }}
		</div>
		<form action="">
			<h2>Wire Transfer Form</h2>
			<br />
			<label for="from-id">Sender's User ID: {{ currentUserId }}</label>
			<br />
			<label for="to-id">Select a recipient: </label>
			<select
				name="to-id"
				v-model="selectedUser.id"
				@change="getRecipientAccId(selectedUser.id)"
				v-bind="receiveAccId"
			>
				<option
					v-for="user in users"
					v-bind:key="user.username"
					v-bind:value="user.id"
				>
					{{ user.username }}
				</option>
			</select>
			<br />
			<label for="amount">Amount to Send: {{ transfer.amount }}</label>
			<input
				type="text"
				name="amount"
				required
				v-model.number="transfer.amount"
				placeholder="0.00"
			/>
			<br />
			<button type="submit" @click.prevent="wireMoney()">Send Money</button>
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
				users: [],
				transfer: {},
				selectedUser: {},
				senderAccount: {},
				receiveAccId: "",
				errorMsg: "",
			};
		},
		created() {
			this.listUsers();
			this.getAccountFrom(this.currentUserId);
		},
		methods: {
			listUsers() {
				accountService.listUsers().then((response) => {
					this.users = response.data;
				});
			},
			getAccountFrom(userId) {
				accountService.getAccountByUserId(userId).then((response) => {
					this.senderAccount = response.data;
				});
			},
			getRecipientAccId(userId) {
				accountService.getAcctIdByUserId(userId).then((response) => {
					this.receiveAccId = response.data;
				});
			},
			wireMoney() {
				const fromId = this.senderAccount.accountId;
				const toId = this.receiveAccId;
				let newTransfer = this.transfer;
				newTransfer.accountFrom = fromId;
				newTransfer.accountTo = toId;
				transferService
					.postTransfer(newTransfer)
					.then((response) => {
						if (response.status === 201) {
							alert("Transaction processed!");
						}
					})
					.catch((error) => {
						this.handleErrorResponse(error, "submitting");
					});
			},
			handleErrorResponse(error, verb) {
				if (error.response) {
					this.errorMsg =
						"Error " +
						verb +
						" application. Response received was " +
						error.response.status +
						" .";
				} else if (error.request) {
					this.errorMsg =
						"Error " + verb + " application. Server could not be reached.";
				} else {
					this.errorMsg =
						"Error " + verb + " application. Request could not be created";
				}
			},
		},
		computed: {
			currentUserId() {
				return this.$store.state.user.id;
			},
		},
	};
</script>

<style scoped></style>
