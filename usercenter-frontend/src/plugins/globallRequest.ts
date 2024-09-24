import { extend } from 'umi-request';
import { message } from 'antd';
import { history } from '@@/core/history';
import { stringify } from 'querystring';

/**
 * 配置request请求时的默认参数
 */
const request = extend({
  // 默认请求是否带上cookie
  credentials: 'include',
  prefix: process.env.NODE_ENV === 'production' ? 'http://user-center.ayangwebsite.cn' : undefined,
});

/**
 * 所有请求拦截器
 */
request.interceptors.request.use((url, options): any => {
  // console.log(`do request url = ${url}`);
  return {
    url,
    options: {
      ...options,
      headers: {},
    },
  };
});

request.interceptors.response.use(async (response, options): Promise<any> => {
  const res = await response.clone().json();
  if (!response.ok) {
    // 错误
    if (res.code === 40100) {
      message.error('请先登录');
      history.replace({
        pathname: '/user/login',
        search: stringify({
          redirect: location.pathname,
        }),
      });
    } else {
      message.error(res.message);
    }
  }
  return res.data;
});

export default request;
