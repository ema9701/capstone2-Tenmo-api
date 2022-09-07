<template>
	<div id="register" class="text-center">
		<v-sheet rounded class="card-register">
			<v-card class="mx-auto" max-width="344" title="Create an account">
				<v-form class="form-register" @submit.prevent="register">
					<v-alert type="error" v-if="registrationErrors">{{
						registrationErrorMsg
					}}</v-alert>
					<v-container>
						<v-text-field
							v-model="user.username"
							class="form-control"
							color="primary"
							label="Username"
							variant="underlined"
							:rules="[required]"
							autofocus
						></v-text-field>

						<v-text-field
							v-model="user.password"
							class="form-control"
							type="Password"
							color="primary"
							label="Password"
							variant="underlined"
							:rules="[required]"
						></v-text-field>

						<v-text-field
							v-model="user.confirmPassword"
							type="Password"
							class="form-control"
							color="primary"
							label="Confirm Password"
							variant="underlined"
							:rules="[required]"
						></v-text-field>
						<router-link class="rlink" :to="{ name: 'login' }"
							>Have an account?</router-link
						>
					</v-container>
					<v-divider></v-divider>
					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn color="success" type="submit">
							Complete Registration
							<v-icon icon="mdi-chevron-right" end></v-icon>
						</v-btn>
					</v-card-actions>
				</v-form>
			</v-card>
		</v-sheet>
	</div>
</template>

<script>
	import authService from "@/services/AuthService";
	export default {
		name: "register-view",
		data() {
			return {
				user: {
					username: "",
					password: "",
					confirmPassword: "",
					role: "user",
				},

				registrationErrors: false,
				registrationErrorMsg: "There were problems registering this user.",
			};
		},
		methods: {
			register() {
				if (this.user.password != this.user.confirmPassword) {
					this.registrationErrors = true;
					this.registrationErrorMsg =
						"Password & Confirm Password do not match.";
				} else {
					authService
						.register(this.user)
						.then((response) => {
							if (response.status == 201) {
								this.$router.push({
									path: "/login",
									query: { registration: "success" },
								});
							}
						})
						.catch((error) => {
							const response = error.response;
							this.registrationErrors = true;
							if (response.status === 400) {
								this.registrationErrorMsg = "Bad Request: Validation Errors";
							}
						});
				}
			},
			clearErrors() {
				this.registrationErrors = false;
				this.registrationErrorMsg =
					"There were problems registering this user.";
			},
			required(v) {
				return !!v || "Field is required";
			},
		},
	};
</script>

<style>
	#register {
		margin-top: 5%;
	}
</style>
