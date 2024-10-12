// node_modules/.pnpm/vue-demi@0.14.10_vue@3.5.11_typescript@5.6.3_/node_modules/vue-demi/lib/index.mjs
var isVue2 = false;
function set(target, key, val) {
  if (Array.isArray(target)) {
    target.length = Math.max(target.length, key);
    target.splice(key, 1, val);
    return val;
  }
  target[key] = val;
  return val;
}
function del(target, key) {
  if (Array.isArray(target)) {
    target.splice(key, 1);
    return;
  }
  delete target[key];
}

export {
  isVue2,
  set,
  del
};
//# sourceMappingURL=chunk-BOE5BV3B.js.map