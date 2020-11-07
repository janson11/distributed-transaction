import Vue from "vue";
import Router from "vue-router";
import list from "@/views/list";
import data from "@/views/data";

Vue.use(Router);

export default new Router({
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "data",
      component: data
    },
    {
      path: "/list/:type",
      name: "list",
      component: list
    }
    // {
    //   path: "/data/:user",
    //   name: "data",
    //   component: data
    // }
  ]
});
