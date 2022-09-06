<template>
	<div class="home">
		<h1>Home</h1>
		<p>You must be authenticated to see this</p>
		<account-details />
	</div>
</template>

<script>
	// @ is an alias to /src
	import AccountDetails from "@/components/AccountDetails";

	export default {
		name: "HomeView",
		components: {
			AccountDetails,
		},
	};
</script>
