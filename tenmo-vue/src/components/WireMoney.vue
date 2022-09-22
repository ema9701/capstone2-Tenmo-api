<template>
	<div id="wire-money">

		<select-users>
			<template v-slot:formSelect>
				<v-btn color="primary"><transfer-form></transfer-form></v-btn>
			</template>
		</select-users>
	</div>
</template>

<script>
	import SelectUsers from "@/components/SelectUsers";
	import TransferForm from "@/components/TransferForm";


	export default {
		name: "wire-money",
		components: { SelectUsers, TransferForm },
		data() {
			return {
				users: [],
				errorMsg: "",
				account: {},
				transferDialog: false,
			};
		}
	}	
</script>

<style></style>
