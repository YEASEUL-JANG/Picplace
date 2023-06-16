<template>
  <!-- Header-->
    <header class="bg-light">
        <div class="container px-5">
            <div class="row gx-5 ">
                <div class="searchBox">
                    <div class="text-center my-5">
                        <h1 class="display-5 fw-bolder text-black mb-2">Present your business in a whole new way</h1>
                        <!-- 검색창 -->
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
                    </div>
                </div>
            </div>
        </div>
    </header>
    <section class="border-bottom" id="features">
        <div class="container  my-5">

<!--            스켈레톤-->
            <el-row gutter="16" v-if="loading">
                <el-col
                    v-for="i in 9"
                    :key="i"
                    :span="8"
                    class="el-col"

                >
            <el-skeleton style="width: 100%"
             animated>
                <template #template>
                    <el-skeleton-item variant="image" style="width: 100%; height: 300px" />
                    <div style="padding: 14px">
                        <el-skeleton-item variant="p" style="width: 50%" />
                        <div style="display: flex;
            align-items: center;
            justify-items: space-between;">
                            <el-skeleton-item variant="text"  />
                            <el-skeleton-item variant="text" style="width: 30%" />
                        </div>
                    </div>
                </template>
            </el-skeleton>
                </el-col>
            </el-row>
<!--        place 목록-->
            <el-row  gutter=16>
                <el-col
                        v-for="(place,index) in places"
                        :key="index"
                        :span="8"
                        class="el-col"
                >
                    <el-card :body-style="{ padding: '0px', cursor:'pointer' }"
                             shadow="hover"
                             @click="placeDetail(place.placeId)">
                        <img :src="'./upload/'+place.placePhotos[0]"
                             class="image"
                        />
                        <div style="padding: 10px">
                            <span>{{ place.name }}</span>
                            <el-tag class="ms-2" type="warning">{{getMenuType(place.menuType)}}</el-tag>
                            <div class="bottom">
                                <time class="time">{{ place.address }}</time>
                                <el-button :icon="Star" type="warning" class="ms-1">찜</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    </section>
</template>

<script>
import {onMounted, ref, watchEffect} from "vue";
import {Search, Star} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";
import axios from "@/common/axios";
import menuTypes from "../model/menuType";
export default {
    name: "PlaceList",
    computed: {
        Star() {
            return Star
        },
        menuTypes() {
            return menuTypes
        },
        Search() {
            return Search
        }
    },
    setup(){
        const router = useRouter();
        //const {triggerToast} = useToast();
        const size = ref('default');
        const loading = ref('false');
        const labelPosition = ref('right')
        const keyword = ref("")
        const category = ref("")
        const placeType = ref([]);
        const placeSearch = ref({
            placeName: '',
            menuKeyword: '',
            address: '',
            placeType: '',
            menuType:''
        })
        const places = ref([]);
        const getMenuType = (mtype) => {
            switch (mtype){
                case menuTypes.KOREAN.text:  return menuTypes.KOREAN.label;
                case menuTypes.WESTERN.text: return menuTypes.WESTERN.label;
                case menuTypes.JAPANESE.text: return menuTypes.JAPANESE.label;
                case menuTypes.CHINESE.text: return menuTypes.CHINESE.label;
                default: return menuTypes.ETC.label;
            }
        }
        watchEffect(()=> {
            if (placeType.value.toString() ==='CAFE') {
                placeSearch.value.placeType = 'CAFE'
                placeSearch.value.menuType = ''
            }else if(placeType.value.toString() ==='RESTAURANT') {
                placeSearch.value.placeType = 'RESTAURANT'
            }else{ placeSearch.value.placeType = ''
            }
        })
        const searchPlace = async () => {
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
            loading.value = true;
            try {
                const res = await axios.post('api/place/placelist', placeSearch.value);
                console.log("##placeList",res.data)
                places.value = res.data
                loading.value = false;
            }catch (err){
                console.log(err);
            }
        }
        onMounted(() => {
            searchPlace()
        });

        const placeDetail =(placeId) => {
            router.push("/detailPlace/"+placeId);
        }
        return{
            size,
            placeSearch,
            labelPosition,
            keyword,
            category,
            searchPlace,
            placeType,
            places,
            getMenuType,
            placeDetail,
            loading
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
.searchBox{
    width: 100%;
}
.el-col{
    margin-bottom: 16px;
}
.el-radio-group{
    margin-right: 12px;
}

.time {
    font-size: 12px;
    color: #999;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.button {
    padding: 0;
    min-height: auto;
}

.image {
    width: 100%;
    display: block;
}
</style>
