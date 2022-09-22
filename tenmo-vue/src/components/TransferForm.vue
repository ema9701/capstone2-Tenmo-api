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
					v-model.number="transfer.transferFrom"
					v-bind="account.userId"
				></v-text-field>
			</template>
			<template v-slot:toId>
				<v-text-field
					label="Recipient UserId*"
					required
					type="number"
					v-model.number="transfer.transferTo"
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
				<v-btn color="blue-darken-1" text type="submit" @click.prevent="wireTransfer()">
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
					transferFrom: "",
					transferTo: "",
					transferAmount: "",
				},
				errorMsg: "",
				account: {},
			};
		},
		created() {
			this.principalAccount(this.currentUserId);
		},
		methods: {
			principalAccount(userId) {
				accountService.getAccountByUserId(userId).then((response) => {
					this.account = response.data;
					console.log(this.transfer.accountFrom);
				});
			},
			wireTransfer() {
				console.log(this.transfer.accountFrom);
				console.log(this.transfer.accountTo);
				console.log(this.transfer.amount);
				transferService.postTransfer(this.transfer).then((response) => {
					if (response.status === 201) {
						alert("Transfer processed!");
					}
				}).catch((error) => {
						this.handleErrorResponse(error, "submitting");
					});
			},
				handleErrorResponse(error, verb) {
					if (error.response) {
						this.errorMsg =
						"Error " +
						verb + " application. Response received was " +
						error.response.status + " .";
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

<style></style>
