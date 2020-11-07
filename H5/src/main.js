import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import VueParticles from "vue-particles";
import VCharts from "v-charts";
import Vcomp from "./components/index";
import Axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import "./assets/iconfont/iconfont.css";
import "./assets/css/global.scss";

Vue.use(VueParticles);
Vue.use(VCharts);
Vue.use(Vcomp);

Vue.prototype.$axios = Axios;
Vue.config.productionTip = "/api";
Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
