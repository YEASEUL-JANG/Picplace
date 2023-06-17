<template>
    <div class="bg-light rounded-3 py-5 px-4 px-md-5 mt-5">
        <div class=" mb-5">
            <h1>Place Register</h1>
    <el-form :model="placeform" label-width="120px" label-position="left" class="mt-5">
        <el-form-item label="매장명">
            <el-input v-model="placeform.name" />
        </el-form-item>
        <el-form-item label="매장주소">
            <el-input v-model="placeform.address" id="address" @click="execDaumPostcode"/>
            <el-input v-model="placeform.detailAddress" id="detailaddress"/>
<!--            <el-select v-model="placeform.region" placeholder="please select your zone">-->
<!--                <el-option label="Zone one" value="shanghai" />-->
<!--                <el-option label="Zone two" value="beijing" />-->
<!--            </el-select>-->
        </el-form-item>
        <el-form-item label="매장유형">
            <el-radio-group v-model="placeform.placeType">
                <el-radio border label="CAFE" name="PlaceType" />
                <el-radio border label="RESTAURANT" name="PlaceType" />
            </el-radio-group>
        </el-form-item>
        <el-form-item label="메뉴타입" v-if="placeform.placeType.toString() === 'RESTAURANT'">
            <el-radio-group v-model="placeform.menuType">
                <el-radio :label="menuTypes.KOREAN.text" name="Menutype">{{menuTypes.KOREAN.label}}</el-radio>
                <el-radio :label="menuTypes.CHINESE.text" name="Menutype">{{menuTypes.CHINESE.label}}</el-radio>
                <el-radio :label="menuTypes.JAPANESE.text" name="Menutype">{{menuTypes.JAPANESE.label}}</el-radio>
                <el-radio :label="menuTypes.WESTERN.text" name="Menutype">{{menuTypes.WESTERN.label}}</el-radio>
                <el-radio :label="menuTypes.ETC.text" name="Menutype">기타</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="운영시간">
            <el-col :span="11">
                <el-time-select
                    v-model="placeform.startTime"
                    start="00:00"
                    step="00:30"
                    end="23:59"
                    format="hh:mm A"
                    placeholder="Pick a startTime"
                    style="width: 100%"
                />
            </el-col>
            <el-col :span="2" class="text-center">
                <span class="text-gray-500">-</span>
            </el-col>
            <el-col :span="11">
                <el-time-select
                    v-model="placeform.endTime"
                    start="00:00"
                    step="00:30"
                    end="23:59"
                    format="hh:mm A"
                    placeholder="Pick a endTime"
                    style="width: 100%"
                />
            </el-col>
        </el-form-item>
        <el-form-item label="매장소개">
            <el-input v-model="placeform.content" type="textarea" />
        </el-form-item>


                <el-form-item
                    v-for="(menu, index) in placeform.menuList"
                    :key="index" :label="'메뉴'+(index+1)" >
                    <el-col :span="12">
                    <el-input v-model="menu.menuName" placeholder="메뉴명" />
                    </el-col>
                    <el-col :span="12">
                    <el-input v-model="menu.price" placeholder="가격"/>
                    </el-col>
<!--                    부트스트랩 업로드-->
                    <div class="input-group mt-2">
                        <input type="file" class="form-control menuImage"  @change="addImage(index)">
                    </div>


                    <el-button class="mt-2" @click.prevent="removeMenu(index)">삭제</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="addDomain">메뉴추가</el-button>
                </el-form-item>
        <el-form-item label="매장사진" >
<!--        매장사진 업로드-->
            <el-upload
                v-model:file-list="rawPlacePhotos"
                id="placeImages"
                list-type="picture-card"
                :on-preview="uploadPlaceImage"
                :auto-upload="false"
            >
                <el-icon><Plus /></el-icon>
            </el-upload>
        </el-form-item>
            <el-dialog v-model="dialogVisible">
                <img w-full :src="placeImageUrl" alt="Preview Image" />
            </el-dialog>



        <el-form-item>
            <el-button type="primary" @click="onSubmit">등록</el-button>
        </el-form-item>
            </el-form>
        </div>
    </div>

    {{placeform}}
    {{rawPlacePhotos}}
</template>

<script>
import {ref} from 'vue'
import {useToast} from "@/common/toast";
import menuTypes from "../../model/menuType";
import axios from "@/common/axios";
import {Plus} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";

export  default {
    components: {Plus},
    computed: {
        menuTypes() {
            return menuTypes
        }
    },
    setup() {
        const {triggerToast} = useToast();
        const router = useRouter();
        const placeform = ref({
            name : '',
            startTime : '',
            endTime: '',
            content: '',
            placeType: '',
            menuType: '',
            address: '',
            detailAddress: '',
            lat: '',
            lng: '',
            zipcode: '',
            menuList: [{
                menuName : '',
                price: 0,
                menuImage: ''
            }],
            placePhotos: []
        })
        const rawPlacePhotos = ref([]);
        const placeImageUrl = ref('');
        const dialogVisible = ref(false);
        const execDaumPostcode = () => {
            new window.daum.Postcode({
                oncomplete: function(data) {
                    var addr = ''; // 주소 변수
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }
                    placeform.value.zipcode = data.zonecode;
                    placeform.value.address = addr;

                    getLALOInfo(addr);


                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("detailaddress").focus();
                }
            }).open();
        }
        const getLALOInfo = async (address)=> {
            const placeAddress = address;
            const url = 'https://dapi.kakao.com/v2/local/search/address.json?query=' + encodeURI(placeAddress);
            const axiosResult = await axios({
                url: url,
                method: 'get',
                headers: {
                    Authorization: 'KakaoAK '+'dad17b185c00ef6c17a4822c4df677fd',
                },
            });
            placeform.value.lat = axiosResult.data.documents[0].address.y;
            placeform.value.lng = axiosResult.data.documents[0].address.x;
            console.log("위도, 경도값 : ",placeform.value.lat+","+placeform.value.lng)
        }
        const formData = new FormData();
        const addDomain = () => {
            placeform.value.menuList.push({
                menuName : '',
                price: 0,
                menuImage: ''
            });
        }
        const removeMenu = (index) => {
            const menu = placeform.value.menuList.indexOf(index)
            if (index !== -1) {
                placeform.value.menuList.splice(menu, 1)
            }
        }
         const addImage = (index) => {
            const file = event.target.files[0];
           placeform.value.menuList[index].menuImage = file.name;
         }
        const uploadPlaceImage= (uploadFile) => {
            placeImageUrl.value = uploadFile.url
            dialogVisible.value = true
        }

        const onSubmit = async () => {
            if(placeform.value.placeType ==='CAFE'){
                placeform.value.menuType = menuTypes.ETC.text
            }

            for(const menu of placeform.value.menuList){
                if(menu.menuName==''||menu.price==0||menu.menuImage=='') {
                    triggerToast("1개 이상의 메뉴를 등록하셔야합니다.","danger")
                    return;
                }
            }
            if(rawPlacePhotos.value.length<1) {
                triggerToast("1개 이상의 매장사진을 등록하셔야합니다.","danger")
                return;
            }

            // //메뉴 파일 추가
            const menuElements = document.getElementsByClassName('menuImage');
            for (let i = 0; i < menuElements.length; i++) {
                const menu = menuElements[i].files[0];
                formData.append('menuImages',menu);
            }
            //매장사진 파일 추가
            rawPlacePhotos.value.forEach((file,index) => {
                const fileObject = new File([file.raw], file.name,{type:file.type})
                formData.append('placeImages',fileObject);
                placeform.value.placePhotos[index] = file.name;
            })
            //업로드 파일 확인
            // for(let file of formData.values()){
            //     console.log(file);
            // }
            try{
                const res = await axios.post('api/place/uploadImage',formData,{
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
                if(res.data.code == 200){
                    console.log("업로드 메뉴갯수 : {}",res.data);
                    const res2 = await axios.post('api/place/addplace',placeform.value);
                    if(res2.data.code == 200){
                        console.log("장소등록 완료 (id반환)  : {}",res2.data);
                        triggerToast('등록완료')
                        router.push({name: "PlaceList"});
                    }
                }else{
                    triggerToast(res.data.message);
                }



            }catch(err){
                console.log(err);
            }
        }
        return {
            placeform,
            onSubmit,
            execDaumPostcode,
            addDomain,
            removeMenu,
            addImage,
            formData,
            placeImageUrl,
            uploadPlaceImage,
            rawPlacePhotos,
            dialogVisible,


        }
    }
}

</script>


<style scoped>

</style>