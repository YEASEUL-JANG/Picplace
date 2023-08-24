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
                    <el-button :icon="Search" @click="onSearch"/>
                </template>
            </el-input>
        </el-form-item>
    </el-form>
</template>

<script>
import {ref, watchEffect} from "vue";
import menuTypes from "../model/menuType";
import {Search} from "@element-plus/icons-vue";
export default {
    name: "SearchForm",
    computed:{
        menuTypes() {
            return menuTypes
        },
        Search() {
            return Search
        },
    },
    props:{

    },
    setup(props, context){
        const placeSearch = ref({
            placeName: '',
            menuKeyword: '',
            address: '',
            placeType: '',
            menuType:'',
            pageNum: 1
        })
        const keyword = ref("")
        const category = ref("")
        const placeType = ref([]);
        const size = ref('default');
        const labelPosition = ref('right')

        watchEffect(()=> {
            if (placeType.value.toString() ==='CAFE') {
                placeSearch.value.placeType = 'CAFE'
                placeSearch.value.menuType = ''
            }else if(placeType.value.toString() ==='RESTAURANT') {
                placeSearch.value.placeType = 'RESTAURANT'
            }else{ placeSearch.value.placeType = ''
            }
        })

        const onSearch = () => {
            switch(category.value){
                case "menu":
                    placeSearch.value.address = ''
                    placeSearch.value.placeName = ''
                    placeSearch.value.menuKeyword = keyword.value; break;
                case "address":
                    placeSearch.value.placeName = ''
                    placeSearch.value.menuKeyword = ''
                    placeSearch.value.address = keyword.value; break;
                case "placename":
                    placeSearch.value.menuKeyword = ''
                    placeSearch.value.address = ''
                    placeSearch.value.placeName = keyword.value; break;
            }
            context.emit('searchPlace',placeSearch.value);
        }

        return{
            placeSearch,
            keyword,
            category,
            onSearch,
            placeType,
            size,
            labelPosition
        }
    }
}
</script>

<style scoped>
.searchForm{
    padding: 20px;
    border-radius: 10px;
    margin: 10px;

}


el-radio-group{
    margin-right: 12px;
}
</style>