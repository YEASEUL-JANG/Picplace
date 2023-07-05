<template>
<div class="container my-5">
    <SearchForm
        @searchPlace="searchPlace"/>
    <div class="card-body ms-1">
        <el-table
                v-loading="state.loading"
                table-layout="auto"
                style="width: 100%"
                :header-cell-style="{ textAlign: 'center' }"
                :data="state.places"
                @row-click="goDetail"
        >
            <el-table-column
                    :label="'번호'"
                    :width="60"
                    align="center"
                    prop="placeId"
            />
            <el-table-column
                    :label="'매장이름'"
                    align="center"
                    prop="name"
            >
            </el-table-column>
            <el-table-column
                    :label="'가게타입'"
                    :width="100"
                    align="center"
                    prop="placeType"
            >
                <template #default="scope">
                    <el-tag class="ms-2" type="info">{{scope.row.placeType}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column :label="'주소'"
                             align="center"
                             prop="address"
            />
            <el-table-column :label="'영업시간'" :width="100">
                <template #default="scope">
                    {{scope.row.startTime}} -<br> {{scope.row.endTime}}
                </template>
            </el-table-column>
            <el-table-column
                :label="'메뉴타입'"
                :width="100"
                align="center"
            >
                <template #default="scope">
                    <el-tag class="ms-2" type="warning">{{getMenuType(scope.row.menuType)}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column :label="'설명'" :width="100" align="center">
                <template #default="scope">
                    <el-tooltip
                            :content="scope.row.content"
                            placement="bottom"
                            effect="dark"
                    >
                        <el-button type="success" size="small" class="btn-primary">설명</el-button>
                    </el-tooltip>
                </template>
            </el-table-column>
            <el-table-column :label="'삭제'" :width="100" align="center">
                <template #default="scope">
                        <el-button type="danger" @click.stop="deletePlace(scope.row.placeId)" size="small" >삭제</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <div style="display: flex; justify-content: center; width: 100%;" class="my-5" >
        <el-pagination background layout="prev, pager, next"  @current-change="handlePageChange" :total="50" />
    </div>
</div>
</template>

<script>
import {computed, onMounted, reactive} from "vue";
import axios from "@/common/axios";
import menuTypes from "@/model/menuType";
import SearchForm from "@/components/SearchForm.vue";
import router from "@/router";
import {useToast} from "@/common/toast";
import store from "@/store";

export default {
    name: "placeList",
    components: {SearchForm},
    setup() {
        const currentUser = computed(() => store.getters["getcurrentUser"]);
        const {triggerToast} = useToast();
        const state = reactive({
            loading: false,
            category : "",
            keyword : "",
            places : [],
            placeSearch : {
                placeName: '',
                menuKeyword: '',
                address: '',
                placeType: '',
                menuType:'',
                pageNum: 1
            }
        })
        const searchPlace = async (placeSearch) => {
            if (placeSearch)
                state.placeSearch = placeSearch
            state.loading = true;
            try {
                const res = await axios.post('api/place/placelist', state.placeSearch);
                console.log("##placeList",res.data)
                state.places = res.data.data
                state.loading = false;
            }catch (err){
                console.log(err);
            }
        }
        onMounted(() => {
            const searchthing = state.placeSearch
            searchPlace(searchthing);
        })

        const getMenuType = (mtype) => {
            switch (mtype){
                case menuTypes.KOREAN.text:  return menuTypes.KOREAN.label;
                case menuTypes.WESTERN.text: return menuTypes.WESTERN.label;
                case menuTypes.JAPANESE.text: return menuTypes.JAPANESE.label;
                case menuTypes.CHINESE.text: return menuTypes.CHINESE.label;
                default: return menuTypes.ETC.label;
            }
        }
        const goDetail = (row) => {
            console.log(row.placeId);
            router.push("/detailPlace/"+row.placeId);
        }
        const deletePlace = async (id) => {
            const userId = {
                userId: currentUser.value.userId
            }
            const res = await axios.post("api/place/placedelete/"+id, userId)
            if(res.data.code == 200) {
                triggerToast("삭제가 완료되었습니다");
                const searchthing = state.placeSearch
                searchPlace(searchthing);
            }else{
                triggerToast("시스템에러","danger")
            }
        }
        const handlePageChange = (number) => {
            console.log(number)
            state.placeSearch.pageNum = number;
            searchPlace();
        }

        return{
            state,
            getMenuType,
            goDetail,
            searchPlace,
            deletePlace,
            handlePageChange

        }
    }
}
</script>

