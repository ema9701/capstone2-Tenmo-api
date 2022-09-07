<template>
	<account-details />
</template>

<script>
	import { defineComponent } from "vue";

	// Components
	import AccountDetails from "@/components/AccountDetails.vue";

	export default defineComponent({
		name: "HomeView",

		components: {
			AccountDetails,
		},
	});
</script>
