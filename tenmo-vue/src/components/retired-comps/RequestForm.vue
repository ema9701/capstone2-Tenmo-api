<template>
	<div id="request-container">
		<form-template>
			<template v-slot:dialogBtn> Request </template>
			<template v-slot:formTitle> Request Form </template>
			<template v-slot:fromId>
				<v-text-field label="Your UserId*" required
				v-model.number="request.requesterId"
				></v-text-field>
			</template>
			<template v-slot:toId>
				<v-text-field label="Requestee UserId*" required
				v-model.number="request.grantorId"></v-text-field>
			</template>
			<template v-slot:cash>
				<v-text-field label="Amount*" required
				v-model.number="request.requestAmount"></v-text-field>
			</template>
			<template v-slot:extra>
				<!-- <v-checkbox-btn
					v-bind:label="`Request approval: ${validate.toString()}`"
					v-model="validate"
				></v-checkbox-btn> -->
			</template>
			<template v-slot:send-btn>
				<v-btn color="blue-darken-1" text @click.prevent="submitRequest()"> Save </v-btn>
			</template>
		</form-template>
	</div>
</template>

<script>
	import FormTemplate from "@/components/FormTemplate";
	import requestService from "@/services/RequestService"
	export default {
		name: "request-form",
		components: {
			FormTemplate,
			
		},
		data() {
			return {
				dialog: false,
				validate: false,
				request: {
					requesterId: "",
					grantorId: "",
					requestAmount: ""
				},
			};
		},
		methods: {
					submitRequest() {
						requestService.postRequest(this.request).then((response) => {
							if (response.status === 201) {
								alert("Request Sent!")
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
			}
		},
	};
</script>

<style></style>
