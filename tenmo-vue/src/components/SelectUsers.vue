<template>
	<div id="select-users">
		<v-card
			class="mx-auto"
			max-width="344"
			variant="outlined"
			v-for="user in users"
			:key="user.id"
		>
			<v-card-item>
				<div>
					<div class="text-overline mb-1">TEnmo User</div>
					<div class="text-h6 mb-1">{{ user.username }}</div>
					<div class="text-caption">
						{{ user.id }}
					</div>
				</div>
			</v-card-item>
			<v-card-actions>
				<v-row justify="center">
					<v-btn color="primary" class="ma-2" @click="dialog = true">
						Send or request
					</v-btn>
					<v-dialog v-model="dialog">
						<v-card>
							<v-card-title> Select a form </v-card-title>
							<v-card-text>
								<slot name="formSelect"></slot>
							</v-card-text>
							<v-card-actions>
								<v-btn color="primary" text @click="dialog = false">
									Close
								</v-btn>
							</v-card-actions>
						</v-card>
					</v-dialog>
				</v-row>
			</v-card-actions>
		</v-card>
	</div>
</template>

<script>
	import accountService from "@/services/AccountService";

	export default {
		name: "select-users",
		data() {
			return {
				users: {},
				dialog: false,
			};
		},
		created() {
			this.list();
		},
		methods: {
			list() {
				accountService.listUsers().then((response) => {
					this.users = response.data;
				});
			},
			selectRecipient(id) {
				this.$router.push({ name: "wire-money", params: { id: id } });
			},
		},
	};
</script>

<style></style>
