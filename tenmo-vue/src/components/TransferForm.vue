<template>
	<div id="transfer-container">
		<form-template>
			<template v-slot:dialogBtn> Transfer </template>
			<template v-slot:formTitle> Transfer Form </template>
			<template v-slot:fromId>
				<v-text-field
					label="Your UserId*"
					type="number"
					required
					v-model.number="transfer.userFromId"
					@change="getAccountFrom(this.currentUserId)"
				></v-text-field>
			</template>
			<template v-slot:toId>
				<v-text-field
					label="Recipient UserId*"
					required
					type="number"
					v-model.number="transfer.userToId"
				></v-text-field>
			</template>
			<template v-slot:cash>
				<v-text-field
					label="Amount*"
					required
					type="number"
					v-model.number="transfer.transferAmount"
				></v-text-field>
			</template>
			<template v-slot: extra>extra</template>
			<template v-slot:send-btn>
				<v-btn color="blue-darken-1" text type="submit" @click.prevent="test()">
					Send
				</v-btn>
			</template>
		</form-template>
	</div>
</template>

<script>
	import FormTemplate from "@/components/FormTemplate";
	import transferService from "@/services/TransferService";
	import accountService from "@/services/AccountService";

	export default {
		name: "transfer-form",
		components: { FormTemplate },
		data() {
			return {
				dialog: false,

				transfer: {
					userFromId: "",
					userToId: "",
					transferAmount: "",
				},
				recipientId: "",
				errorMsg: "",
				account: {},
			};
		},
		created() {
			this.getAccountFrom(this.currentUserId);
		},
		methods: {
			getRecipientAccId(userId) {
				accountService.getAcctIdByUserId(userId).then((response) => {
					this.recipientId = response.data;
					console.log(this.transfer.accountTo);
				});
			},
			getAccountFrom(userId) {
				accountService.getAcctIdByUserId(userId).then((response) => {
					this.account = response.data;
					console.log(this.transfer.accountFrom);
				});
			},
			wireMoney() {
				console.log(this.transfer.accountFrom);
				console.log(this.transfer.accountTo);
				console.log(this.transfer.amount);
				transferService
					.postTransfer(this.transfer)
					.then((response) => {
						if (response.status === 201) {
							alert("Transaction Processed!");
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
			test() {
				console.log(this.transfer.accountFrom);
				console.log(this.transfer.accountTo);
				console.log(this.transfer.amount);
				transferService.test(this.transfer).then((response) => {
					if (response.status === 201) {
						alert("It worked");
					}
				});
			},
		},
		computed: {
			currentUserId() {
				return this.$store.state.user.id;
			},
		},
	};
</script>

<style></style>
