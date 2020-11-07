<template>
  <div class="data-page">
    <dheader></dheader>
    <div class="data-content">
      <table class="table">
        <thead>
          <tr>
            <th class="col-md-2">服务名</th>
            <th class="col-md-2">RM版本号</th>
            <th class="col-md-4">提交数量</th>
            <th class="col-md-4">回滚数量</th>
            <th class="col-md-4">正在执行数量</th>
            <th class="col-md-4">异常数量</th>
            <th class="col-md-4">超时时间</th>
            <th class="col-md-4">超分布式连接占用比例</th>
            <th class="col-md-4">分布式连接占用数量</th>
            <th class="col-md-4">
              <button type="button" class="btn btn-default">查看</button>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in getData" :key="index">
            <td class="col-md-2">
              {{ item.created_at.substring(0, 10) }}
            </td>
            <td class="col-md-2">{{ item.type }}</td>
            <td class="col-md-4">{{ item.repo.name }}</td>
            <td class="col-md-4">{{ item.payload.commits }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <nouser :isShow="isShow" v-if="isShow"></nouser>
    <loading v-if="pageShow"></loading>
  </div>
</template>

<script>
import dheader from '../data/dheader'

export default {
  components: {
    dheader
  },
  data() {
    return {
      isShow: false,
      pageShow: false,
      personalData: {},
      numberData: {
        total: 83455,
        normal: 34556,
        warning: 244
      },
      username: ''
    }
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.$axios
        .get('https://api.github.com/users/' + 'be-careful')
        .then((response) => {
          let res = JSON.parse(JSON.stringify(response))
          if (res.status === 200) {
            this.username = username
            let data = res.data
            let sinceDate = data.created_at
            let joinDate = sinceDate.substring(0, 10)
            let img = data.avatar_url
            let objP = {
              username: username,
              joinDate: joinDate,
              img: img,
            }
            this.personalData = objP
            //仓库数、粉丝数、跟随数
            let pubRepos = data.public_repos
            let followers = data.followers || 83450
            let following = data.following
            let objN = {
              pubRepos: pubRepos,
              followers: followers,
              following: following,
            }
            this.numberData = objN
            this.pageShow = false
          }
          return
        })
        .catch((err) => {
          this.pageShow = false
          this.isShow = true
          console.log(err.message)
        })
    },
  },
}
</script>

<style lang="scss">
.data-page {
  background: url(../../assets/data/true.png) repeat-x;
  top: 0;
  right: 0;
  right: 0;
  bottom: 0;
  height: 100%;
  min-width: 1220px;
  .data-content {
    padding: 20px 0;
    .data-time {
      width: 340px;
      height: 35px;
      background-color: #2c58a6;
      line-height: 35px;
      color: #fff;
      font-size: 12.8px;
      margin-bottom: 25px;
      margin-left: 20px;
      text-align: center;
    }
    .data-main {
      width: calc(100% - 40px);
      margin-bottom: 40px;
      margin-left: 20px;
      height: 720px;

      .main-left {
        width: 24%;
        float: left;
      }
      .main-center {
        float: left;
        width: 52%;
        padding: 0 20px 0 20px;
      }
      .main-right {
        float: left;
        width: 24%;
        height: 615px;
      }
    }
  }
}
</style>
