<template>
	<div id="update">
		<form class="form-update" @submit.prevent="changePassword()">
			<h2 class="h3 mb-3 font-weight-normal">Please Change Your Password</h2>
			<div class="alert alert-danger" role="alert" v-if="invalidCredentials">
				Invalid username or password!
			</div>
			<br />
			<label for="password" class="sr-only">New Password: </label>
			<input
				type="password"
				id="password"
				class="form-control"
				placeholder="New Password"
				v-model="user.password"
				required
			/>
			<br />
			<br />
			<label for="confirmPassword" class="sr-only">Confirm Password: </label>
			<input
				type="password"
				id="confirmPassword"
				class="form-control"
				placeholder="New Password"
				v-model="user.confirmPassword"
				required
			/>
			<br /><br />
			<button id="update-password" type="submit">Change password</button>
			<br />
			<button type="reset">Reset Form</button>
		</form>
	</div>
</template>

<script>
	import authService from "@/services/AuthService";
	export default {
		data() {
			return {
				user: {},
				invalidCredentials: false,
			};
		},
		methods: {
			changePassword() {
				if (this.user.password !== this.user.confirmPassword) {
					alert("Passwords do not match, try again.");
				} else
					authService
						.changePassword(this.user)
						.then((response) => {
							if (response.status === 201) {
								alert("Password changed!");
								this.user = response.data;
								this.$router.push("/");
							}
						})
						.catch((error) => {
							const response = error.response;
							if (response.status === 403) {
								this.invalidCredentials = true;
							}
						});
			},
		},
	};
</script>

<style scoped></style>
