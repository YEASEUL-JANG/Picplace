<template>
    <div class="bg-light rounded-3 py-5 px-4 px-md-5 my-5  justify-content-center" >
        <div class="text-center mb-5">
            <h1 class="fw-bolder">Profile</h1>
        </div>
        <div class="d-flex flex-column">
        <el-form :model="userform" class="mx-auto" label-width="120px">
            <el-form-item label="이름" >
                <el-input disabled v-model="userform.username" />
            </el-form-item>
            <el-form-item label="주소">
                <el-input  :disabled="!onEdit" v-model="userform.zipcode"/>
                <el-input  @click="execDaumPostcode" :disabled="!onEdit" v-model="userform.address" />
            </el-form-item>
            <el-form-item label="상세주소" >
                <el-input id="detailAddress" :disabled="!onEdit" v-model="userform.detailAddress" />
            </el-form-item>
            <el-form-item label="가입일시" >
                <el-input disabled :value="userform.joinDate"/>
            </el-form-item>
            <div class="d-flex justify-content-center">
                <el-button v-if="!onEdit" size=large  type="primary" class="m-3" @click="onEditOn">수정하기</el-button>
                <el-button v-else size=large  type="success" class="m-3" @click="onEditProfile">확인</el-button>
            </div>
        </el-form>
        </div>

    </div>
</template>
<script>
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import axios from "@/common/axios";
import {useToast} from "@/common/toast";

export default {
    name: "Profile",
    setup(){
        const route = useRoute();
        const userId= route.params.userId;
        const userform = ref({})
        const onEdit = ref(false);
        const {triggerToast} = useToast();

        onMounted( ()=> {
            console.log("프로필접속 유저Id: ",userId)
            getUserProfile();
        })

        const getUserProfile = async () => {
            const res = await axios.get("api/user/findUser/"+userId)
            console.log("getUserProfile : ",res.data)
            if(res.data.code == 200){
                userform.value = res.data.data
                userform.value.joinDate = new Date (res.data.data.joinDate).toISOString().slice(0,10)
            }
        }
        const onEditOn = () => {
            onEdit.value = true;
        }
        const onEditProfile = async () => {
            const res = await axios.put("api/user/editUser/"+userId,userform.value)
            if(res.data.code==200) {
                triggerToast("수정 완료되었습니다")
                onEdit.value = false;
                getUserProfile()
            }else{
                triggerToast("수정 실패")
            }
        }
        const execDaumPostcode = () => {
            new window.daum.Postcode({
                oncomplete: function(data) {
                    var addr = ''; // 주소 변수
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }
                    userform.value.zipcode = data.zonecode;
                    userform.value.address = addr;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("detailAddress").focus();
                }
            }).open();
        }

        return{
            userform,
            onEdit,
            onEditOn,
            onEditProfile,
            execDaumPostcode

        }
    }
}
</script>

<style scoped>
.formInput{
    width: 300px;
}
</style>