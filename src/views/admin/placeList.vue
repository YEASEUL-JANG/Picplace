<template>
<div class="container my-5">
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
                    type="index"
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
        </el-table>
        <!-- PAGINATION -->
<!--        <div class="row">-->
<!--            <div-->
<!--                    class="pt-8 col-12 d-flex align-items-center justify-content-center"-->
<!--            >-->
<!--                <Paginator-->
<!--                        ref="paginator"-->
<!--                        :cntPerPage="pager.size"-->
<!--                        :itemCount="pager.totalCnt"-->
<!--                        @changedPage="onChangedPage"-->
<!--                />-->
<!--            </div>-->
<!--        </div>-->
    </div>

</div>
</template>

<script>
import {onMounted, reactive, ref} from "vue";
import axios from "@/common/axios";
import menuTypes from "@/model/menuType";

export default {
    name: "placeList",
    setup() {

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
                menuType:''
            }
        })
        const loading = ref(false);

        const getPlaceList = async () => {
            switch (state.category) {
                case "menu":
                    state.placeSearch.address = ''
                    state.placeSearch.placeName = ''
                    state.placeSearch.menuKeyword = state.keyword;
                    break;
                case "address":
                    state.placeSearch.placeName = ''
                    state.placeSearch.menuKeyword = ''
                    state.placeSearch.address = state.keyword;
                    break;
                case "placename":
                    state.placeSearch.menuKeyword = ''
                    state.placeSearch.address = ''
                    state.placeSearch.placeName = state.keyword;
                    break;
            }
            loading.value = true;
            try {
                const res = await axios.post('api/place/placelist', state.placeSearch)
                console.log("#####placeList",res.data)
                state.places = res.data
                loading.value = false;

            } catch (err) {
                console.log(err)

            }
        }
        onMounted(() => {
            getPlaceList();
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
        const goDetail = () => {

        }

        return{
            state,
            getMenuType,
            goDetail

        }
    }
}
</script>

