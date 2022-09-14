<template>
	<div id="request-container">
		<form-template>
			<template v-slot:dialogBtn> Request </template>
			<template v-slot:formTitle> Request Form </template>
			<template v-slot:fromId>
				<v-text-field label="Your UserId*" required></v-text-field>
			</template>
			<template v-slot:toId>
				<v-text-field label="Requestee UserId*" required></v-text-field>
			</template>
			<template v-slot:cash>
				<v-text-field label="Amount*" required></v-text-field>
			</template>
			<template v-slot:extra>
				<v-checkbox-btn
					v-bind:label="`Request approval: ${validate.toString()}`"
					v-model="validate"
				></v-checkbox-btn>
			</template>
			<template v-slot:send-btn>
				<v-btn color="blue-darken-1" text @click="dialog = false"> Save </v-btn>
			</template>
		</form-template>
	</div>
</template>

<script>
	import FormTemplate from "@/components/FormTemplate";
	export default {
		name: "request-form",
		components: {
			FormTemplate,
		},
		data() {
			return {
				dialog: false,
				validate: false,
			};
		},
	};
</script>

<style></style>
