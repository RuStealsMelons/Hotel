<template>
  <!--顶部 start-->
  <TopHeader/>
  <!--顶部 start-->

  <!--房间分类 搜索 幻灯片 start-->
  <div class="hotel-room-nav hotel-index">

    <!--搜索 start-->
    <SearchKey/>
    <!--搜索 end-->

    <!--幻灯片 房间分类 start-->
    <div class="hotel-banner">
      <!--房间分类 start-->
      <div class="hotel-container">
        <div class="product-list">
          <dl id="getIndexRoomType">
            <dt>
              <router-link to="/hotel/list">
              房间分类
              </router-link>
            </dt>
            <dd v-for="item in indexRoomTypeList">
              <router-link  :to="{path: '/hotel/list',query:{id:item.id}}" >
                {{item.typeName}}
              </router-link>
            </dd>
          </dl>
        </div>

      </div>
      <!--房间分类 end-->

      <!--幻灯片 start-->
      <el-carousel height="460px">
        <el-carousel-item>
          <img src="@/assets/hotel/images/banner3.jpg" alt="酒店管理系统">
        </el-carousel-item>
        <el-carousel-item>
          <img src="@/assets/hotel/images/banner2.jpg" alt="酒店管理系统">
        </el-carousel-item>
        <el-carousel-item>
          <img src="@/assets/hotel/images/banner4.jpg" alt="酒店管理系统">
        </el-carousel-item>
        <el-carousel-item>
          <img src="@/assets/hotel/images/banner5.jpg" alt="酒店管理系统">
        </el-carousel-item>
      </el-carousel>
      <!--幻灯片 end-->
    </div>
    <!--幻灯片 房间分类 start-->
  </div>
  <!--房间分类 搜索框 幻灯片 end-->

  <!--酒店楼层和房间 start-->
  <div class="hotel-temp">
    <template v-for="(item,index) in hotelFloorList">
      <!--temp-hot start-->
      <div v-if="index%2==0" class="temp-hot">
        <div class="hotel-container">
          <p class="temp-title-cn"><span></span>
            <router-link  :to="{path: '/hotel/list',query:{floorId:item.id}}" >
              {{item.floorName}}
            </router-link>
            <span></span></p>
          <el-row :gutter="20">
            <template v-for="room in item.roomList ">
              <el-col :span="6">
                <router-link target="_blank" class="template store-list-box " :to="'/hotel/details/'+room.id">
                  <img :src="url+'uploadFile/'+room.coverImg"  class="store-list-cover">
                  <h2>{{room.roomName}}</h2>
                  <p class="price"> <span title="金额"> ￥{{room.memberPrice}} </span> <span title="房号" style="color:  #fff;background: #0e88cc;padding: 3px;text-align: center;border: 1px solid #4cffb3;font-size: 13px;"> NO.{{room.roomNumber}} </span></p>

                </router-link>
              </el-col>
            </template>

          </el-row>
        </div>
      </div>
      <!--temp-hot end-->
      <!--temp-normal start-->
      <div v-else class="temp-normal">
        <div class="hotel-container">
          <p class="temp-title-cn"> <span></span>
            <router-link  :to="{path: '/hotel/list',query:{floorId:item.id}}" >
              {{item.floorName}}
            </router-link>
            <span></span> </p>
          <el-row :gutter="20">
            <template v-for="room in item.roomList ">
            <el-col :span="6">
              <router-link target="_blank" class="template store-list-box " :to="'/hotel/details/'+room.id">
                <img :src="url+'uploadFile/'+room.coverImg"  class="store-list-cover">
                <h2 class="layui-elip">{{room.roomName}}</h2>
                <div> <label class="layui-badge-rim store-list-pay"> ￥{{ room.memberPrice }} </label>
                  <div class="store-list-colorbar" title=""> <span class="store-color-bar" style="color:  #fff;background: #cc9812;padding: 3px;text-align: center;">NO.{{room.roomNumber}}</span>
                  </div>
                </div>
              </router-link>
            </el-col>
            </template>
          </el-row>
        </div>
      </div>
      <!--temp-normal end-->
    </template>
  </div>
  <!--酒店楼层和房间 end-->

  <!--底部 start-->
  <Footer/>
  <!--底部 end-->

</template>

<script setup lang="ts">
import { Search } from '@element-plus/icons-vue'
import { ref,onMounted } from 'vue'
import {getIndexApi} from "@/api/hotel"
import TopHeader from "../comm/header/TopHeader.vue"
import Footer from "../comm/footer/Footer.vue"
import SearchKey  from "../comm/search/Search.vue"
// 服务器路径
const url = import.meta.env.VITE_APP_BASE_API
// 获取首页房间类型
const indexRoomTypeList = ref<object[]>([])
// 获取首页楼层及房间列表
const hotelFloorList = ref<object[]>([])
const getIndex = async ()=> {
  const { data} = await getIndexApi()
  console.log('----data-----',data)
  indexRoomTypeList.value = data.result.indexRoomTypeList
  hotelFloorList.value = data.result.hotelFloorList
}



onMounted(() => {
  getIndex()
})
</script>

<style scoped>
/**顶部样式 start */
.hotel-header {
  height: 59px;
  border-bottom: 1px solid #404553;
  background-color: #083a6d;
  z-index: 1000;
  position: relative;
}
.hotel-container {
  width: 1350px;
  padding: 0;
  position: relative;
  margin: 0 auto;
  box-sizing: border-box;
}

 .hotel-container .logo {
  left: 15px;
   position: absolute;
   top: 16px;
}

.hotel-container .logo img {
  width: 82px;
  height: 31px;
  border: none;
}

.hotel-component {
  position: absolute;
  width: 160px;
  left: 120px;
  top: 16px;
}

.hotel-container .hotel-nav {
  right: 15px;
  position: absolute;
  top: 0;
  padding: 0;
  background: none;
  color: #fff;
  border-radius: 2px;
  box-sizing: border-box;
}
.hotel-nav .hotel-nav-item {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  line-height: 60px;
  margin: 0 20px;
}
.hotel-nav * {
  font-size: 14px;
}

.hotel-case-active {
  color: #fff;
}
.hotel-nav .hotel-nav-item a {
  display: block;
  padding: 0 20px;
  color: #fff;
  color: rgba(255,255,255,.7);
  transition: all .3s;
  -webkit-transition: all .3s;
}

.hotel-active {
  color: #fff!important;
}

.hotel-nav .hotel-nav-item a:hover, .hotel-nav .hotel-active a {
  color: #fff;
}

.hotel-nav .hotel-active:after {
  content: '';
  top: auto;
  bottom: 0;
  width: 100%;
}

.hotel-nav .hotel-active:after {
  position: absolute;
  left: 0;
  height: 5px;
  background-color: #5FB878;
  transition: all .2s;
  -webkit-transition: all .2s;
}

/** 顶部样式 end*/


/**房间分类 搜索 幻灯片样式 start*/
.hotel-room-nav {
  background-color: #fff;
}

.hotel-room-nav.hotel-index {
  padding-bottom: 0;
}

.hotel-room-nav .input-search {
  width: 100%;
  padding: 30px 0 10px;
  text-align: center;
  background-color: #FFF;
}
.hotel-room-nav .input-search .el-input {
  vertical-align: middle;
  width: 500px;
  height: 40px;
  background: #d9dfe0;
  border: none;
  outline: none;
  border-top-left-radius: 2px;
  border-bottom-left-radius: 2px;
}

/**房间分类 start*/
.hotel-room-nav .hotel-banner {
  margin-top: 70px;
  background: #677ee4;
  text-align: center;
}

.hotel-room-nav .product-list {
  position: absolute;
  left: 0;
  width: 220px;
  z-index: 100;
  top: -60px;
  background: #fff;
  box-shadow: 0px 1px 5px rgb(0 0 0 / 15%);
}

.hotel-room-nav .product-list dl dt {
  font-size: 16px;
  background: #5e6eba;
  line-height: 60px;
}

.hotel-room-nav .product-list dl dt a {
  color: #FFF;
}
.hotel-room-nav .product-list dl dd {
  display: block;
  width: 100%;
  margin: 0;
  line-height: 65px;
  border: none;
  border-bottom: 1px solid #f0f0f0;
}
/**房间分类 end*/
/**房间分类 搜索 幻灯片样式 end*/


/**酒店楼层和房间 start*/
.hotel-temp .temp-hot {
  padding-top: 80px;
  padding-bottom: 80px;
  background: #FFF;
  text-align: center;
}

.hotel-temp .temp-hot p {
  padding-bottom: 10px;
  text-align: center;
  font-size: 16px;
}
.temp-title-cn {
  margin-bottom: 52px;
  color: #333;
  font-size: 28px!important;
  line-height: 36px;
  position: relative;
  text-align: center;
  font-weight: 300;
}

.temp-title-cn span {
  width: 35px;
  height: 2px;
  background: #333;
  display: inline-block;
  position: absolute;
  top: 16px;
  left: 50%;
  margin-left: -110px;
}

.temp-title-cn span:last-child {
  margin-left: 70px;
}

.store-list-box {
  display: block;
  background-color: #FFF;
  transition: all .5s;
  -webkit-transition: all .5s;
  padding-bottom: 6px;
}

.store-list-box .store-list-cover {
  height: 193px;
}

.store-list-box h2 {
  font-size: 18px;
  padding: 20px 15px;
  line-height: 26px;
  text-align: center;
}

.hotel-temp .temp-hot p {
  padding-bottom: 10px;
  text-align: center;
  font-size: 16px;
}

.hotel-temp .temp-hot p.price span {
  line-height: 14px;
  color: #f44e2d;
  margin-right: 15px;
}

.hotel-temp .temp-normal {
  padding-top: 60px;
  padding-bottom: 60px;
}
.store-list-box>div {
  position: relative;
  padding: 0 15px 15px;
}

.store-list-pay {
  border-color: #FF5722;
  color: #FF5722;
}

.store-list-colorbar {
  position: absolute;
  right: 15px;
  top: -1px;
}

.store-color-bar {
  display: inline-block;
  width: 67px;
  height: 20px;
}


/**酒店楼层和房间 end*/

/*底部样式 start*/
.hotel-footer {
  margin: 50px 0 0;
  padding: 20px 0 30px;
  line-height: 30px;
  text-align: center;
  color: #737573;
  border-top: 1px solid #e2e2e2;
}

.hotel-footer a {
  padding: 0 6px;
  font-weight: 300;
  color: #333;
}
/*底部样式 end*/
</style>
