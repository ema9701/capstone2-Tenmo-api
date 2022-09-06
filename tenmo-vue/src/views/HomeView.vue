<template>
	<hello-world />
</template>

<script>
	import { defineComponent } from "vue";

	// Components
	import HelloWorld from "@/components/AccountDetails.vue";

	export default defineComponent({
		name: "HomeView",

		components: {
			HelloWorld,
		},
	});
</script>
