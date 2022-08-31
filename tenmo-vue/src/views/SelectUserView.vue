<template>
	<div>
		<select-users />
	</div>
</template>

<script>
	import SelectUsers from "@/components/SelectUsers";
	export default {
		data() {
			return {
				components: { SelectUsers },
			};
		},
	};
</script>

<style scoped></style>
