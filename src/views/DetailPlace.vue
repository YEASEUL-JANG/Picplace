<template>
    <div class="container px-4 px-lg-5">
        <!-- Heading Row-->
        <div class="row gx-4 gx-lg-5 align-items-center my-5">
            <div class="col-lg-7">
<!--                매장사진-->
                <el-carousel indicator-position="outside" trigger="click">
                    <el-carousel-item v-for="(photo,index) in place.placePhotos" :key="index">
                        <img :src="require('../../public/upload/'+photo)"
                              style="width: 100% " height="300" class="image"/>

                    </el-carousel-item>
                </el-carousel>

            </div>
<!--            메뉴테이블-->
            <div class="col-lg-5">
                <h1 class="font-weight-light">{{ place.name }}</h1>
                <el-tag class="ms-2" type="warning">{{getMenuType(place.menuType)}}</el-tag>
                <p>{{place.content}}</p>

                    <el-table :data="place.menuList" border style="width: 100%" height="300">
                        <el-table-column label="메뉴">
                            <template v-slot="scope">
                                <img :src="require('../../public/upload/'+scope.row.menuImage)" style="width: 100%; height: auto;" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="menuName" label="메뉴이름" />
                        <el-table-column prop="price" label="가격"  />
                    </el-table>
                <el-button :icon="Star" type="warning" class="m-3" @click="addPicplace(place.placeId)">찜</el-button>
            </div>
        </div>
        <!-- Call to Action-->
            <el-table stripe style="width: 100%">
                <el-table-column prop={{place.startTime}} label="Date"  width="100" />
                <el-table-column prop={place.endTime}} label="Name" width="100" />
                <el-table-column label="Address" width="100">
                    <template v-slot="scope">
                        <span>{{ scope.place.value.address }}</span>
                    </template>
                </el-table-column>
            </el-table>
    </div>
    {{place}}
</template>

<script>
import {useRoute, useRouter} from "vue-router";
import {useToast} from "@/common/toast";
import {computed, onMounted, ref} from "vue";
import axios from "@/common/axios";
import {Star} from "@element-plus/icons-vue";
import menuTypes from "@/model/menuType";
import store from "@/store";

export default {
    name: "DetailPlace",
    computed: {
        Star() {
            return Star
        }
    },
    setup() {
        const router = useRouter();
        const {triggerToast} = useToast();
        const route = useRoute();
        const placeId = route.params.placeId;
        const place = ref({});
        onMounted(async () => {
            try{
            const res = await axios.get("api/place/placedetail/"+placeId);
            place.value = res.data;
            }catch (err){
                console.log(err);
            }
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
        const currentUser = computed(() => store.getters["getcurrentUser"]);
        const addPicplace = async(placeId) => {
            try{
            const userid = currentUser.value.userId;
            console.log("유저아이디 : "+userid+" place아이디 : "+placeId);
            const res = await axios.post("api/place/placePic/"+placeId,{
                userId : userid
            });
            if(res.data > -1){
                triggerToast("찜목록에 추가되었습니다.");
            }
            }catch (err){
                if (err?.response?.status === 409) {
                    triggerToast ("이미 찜한 장소입니다.","danger");
                } else {
                    triggerToast ("Unexpected error occurred","danger");
                }
                console.log(err);
            }
        }


        return{
            place,
            getMenuType,
            addPicplace,


        }
    }

}
</script>

<style scoped>
</style>