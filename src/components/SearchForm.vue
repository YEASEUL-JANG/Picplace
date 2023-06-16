<template>
    <el-form
            class="bg-light searchForm"
            ref="form"
            :model="placeSearch"
            label-width="auto"
            :label-position="labelPosition"
            :size="size"
    >

        <el-form-item label="PlaceType">
            <el-checkbox-group v-model="placeType">
                <el-checkbox-button label="CAFE" name="placeType" />
                <el-checkbox-button label="RESTAURANT" name="placeType" />
            </el-checkbox-group>
        </el-form-item>
        <el-form-item label="MenuType" v-if="!(placeType.toString()==='CAFE'||placeType.toString()==='')" prop="menuType">
            <el-radio-group v-model="placeSearch.menuType">
                <el-radio :label="menuTypes.ETC.text" name="menuType">{{menuTypes.ETC.label}}</el-radio>
                <el-radio :label="menuTypes.KOREAN.text" name="menuType">{{menuTypes.KOREAN.label}}</el-radio>
                <el-radio :label="menuTypes.CHINESE.text" name="menuType">{{menuTypes.CHINESE.label}}</el-radio>
                <el-radio :label="menuTypes.JAPANESE.text" name="menuType">{{menuTypes.JAPANESE.label}}</el-radio>
                <el-radio :label="menuTypes.WESTERN.text" name="menuType">{{menuTypes.WESTERN.label}}</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="Search">
            <el-input
                    v-model="keyword"
                    placeholder="검색어를 입력하세요."
                    class="input-with-select"
            >
                <template #prepend>
                    <el-select v-model="category" placeholder="Select" style="width: 115px">
                        <el-option label="메뉴" value="menu" />
                        <el-option label="주소" value="address" />
                        <el-option label="매장명" value="placename" />
                    </el-select>
                </template>
                <template #append>
                    <el-button :icon="Search" @click="searchPlace"/>
                </template>
            </el-input>
        </el-form-item>

    </el-form>
</template>

<script>
export default {
    name: "SearchForm"
}
</script>

<style scoped>

</style>