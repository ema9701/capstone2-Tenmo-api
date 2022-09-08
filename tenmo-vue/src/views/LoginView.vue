<template>
	<div id="login" class="text-center">
		<!-- <v-sheet rounded class="card-login"> -->
		<v-card class="mx-auto" max-width="344" title="Sign In">
			<v-form v-model="form" @submit.prevent="login">
				<v-alert type="error" v-if="invalidCredentials"
					>Invalid username and password!</v-alert
				>
				<v-alert type="success" v-if="this.$route.query.registration"
					>Thank you for registering, please sign in.</v-alert
				>
				<v-container>
					<v-text-field
						v-model="user.username"
						:rules="[required]"
						class="mb-2"
						clearable
						label="username"
					></v-text-field>

					<v-text-field
						v-model="user.password"
						type="Password"
						:rules="[required]"
						clearable
						label="Password"
						placeholder="Enter your password"
					></v-text-field>

					<router-link :to="{ name: 'register' }"
						>New to TEnmo? Register here</router-link
					>
					<br />
					<br />
					<v-btn
						:disabled="!form"
						block
						color="success"
						size="large"
						type="submit"
						variant="elevated"
					>
						Sign In
					</v-btn>
				</v-container>
			</v-form>
		</v-card>
		<!-- </v-sheet> -->
	</div>
</template>

<script>
	import authService from "@/services/AuthService";

	export default {
		name: "login-view",
		components: {},
		data() {
			return {
				user: {
					username: "",
					password: "",
				},
				form: false,
				invalidCredentials: false,
			};
		},
		methods: {
			login() {
				authService
					.login(this.user)
					.then((response) => {
						if (response.status == 200) {
							this.$store.commit("SET_AUTH_TOKEN", response.data.token);
							this.$store.commit("SET_USER", response.data.user);
							this.$router.push("/");
						}
					})
					.catch((error) => {
						const response = error.response;

						if (response.status === 401) {
							this.invalidCredentials = true;
						}
					});
			},
			required(v) {
				return !!v || "Field is required";
			},
		},
	};
</script>

<style>
	#login {
		margin-top: 5%;
	}
</style>
