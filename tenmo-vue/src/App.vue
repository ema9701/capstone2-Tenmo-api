<template>
	<v-app>
		<navbar />
		<v-main transition="slide-x-transition">
			<v-container fluid>
				<router-view />
			</v-container>
		</v-main>
		<footer-bar />
	</v-app>
</template>

<script>
	import Navbar from "@/components/Navbar.vue";
	import FooterBar from "@/components/FooterBar";
	export default {
		name: "App",
		components: {
			Navbar,
			FooterBar,
		},
		data: () => ({}),
	};
</script>
