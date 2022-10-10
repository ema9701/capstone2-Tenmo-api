<template>
	<div id="form-container">
		<v-row justify="center">
			<v-dialog v-model="dialog" persistent>
				<template v-slot:activator="{ props }">
					<v-btn color="primary" v-bind="props">
						<slot name="dialogBtn">Open form </slot>
					</v-btn>
				</template>
				<v-card>
					<v-card-title>
						<span class="text-h5">
							<slot name="alert-popup"></slot>
							<slot name="formTitle">Form type</slot>
						</span>
					</v-card-title>
					<v-card-text>
						<v-container>
							<v-row>
								<v-col cols="6">
									<slot name="fromId">
										<v-text-field label="Your UserId*" required></v-text-field>
									</slot>
								</v-col>
								<v-col cols="6">
									<slot name="toId">
										<v-text-field
											label="Recipient UserId*"
											required
										></v-text-field>
									</slot>
								</v-col>
								<v-col cols="6">
									<slot name="cash">
										<v-text-field label="Amount*" required></v-text-field>
									</slot>
								</v-col>
								<v-col cols="6">
									<slot name="extra"></slot>
								</v-col>
							</v-row>
						</v-container>
						<small>*indicates required field</small>
					</v-card-text>
					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn color="blue-darken-1" text @click="dialog = false">
							Close
						</v-btn>
						<slot name="send-btn">
							<v-btn color="blue-darken-1" text @click="dialog = false">
								Save
							</v-btn>
						</slot>
					</v-card-actions>
				</v-card>
			</v-dialog>
		</v-row>
	</div>
</template>

<script>
	export default {
		name: "form-template",
		data() {
			return {
				dialog: false,
			};
		},
	};
</script>

<style></style>
