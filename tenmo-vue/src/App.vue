<template>
	<v-app>
		<navbar />
		<v-main transition="slide-x-transition">
			<router-view />
		</v-main>
	</v-app>
</template>

<script>
	import Navbar from "@/components/Navbar.vue";
	export default {
		name: "App",
		components: {
			Navbar,
		},
		data: () => ({
			//
		}),
	};
</script>
