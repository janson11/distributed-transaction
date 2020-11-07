import particles from "./particles";
import github from "./github";
import icon from "./icon";
import header from "./header";
import foot from "./foot";
import bg from "./bg";
import databox from "./databox";
import nodata from "./nodata";
import loading from "./loading";
const components = {
  header,
  foot,
  bg,
  icon,
  particles,
  github,
  databox,
  nodata,
  loading
};

const install = (Vue = {}) => {
  if (install.installed) return;
  Object.keys(components).forEach(component => {
    Vue.component(components[component].name, components[component]);
  });
  Vue.prototype.$notice = Notification;
  // Vue.prototype.$message = Message
  install.installed = true;
};
install.installed = false;
if (typeof window !== "undefined" && window.Vue) {
  install(window.Vue);
  install.installed = true;
}

const Vcomp = {
  ...components,
  install
};

export default Vcomp;
