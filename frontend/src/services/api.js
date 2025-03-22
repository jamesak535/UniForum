import axios from 'axios';

const API_URL = 'http://localhost:8080/api/';

const api = axios.create({
  baseURL: API_URL,
});

// Request interceptor for adding auth token
api.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.token) {
      config.headers['Authorization'] = `Bearer ${user.token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Topic service
export const topicService = {
  getAll: (page = 0, size = 10, sort = 'createdAt', direction = 'desc') => {
    return api.get(`topics?page=${page}&size=${size}&sort=${sort}&direction=${direction}`);
  },
  getById: (id) => {
    return api.get(`topics/${id}`);
  },
  create: (topicData) => {
    return api.post('topics', topicData);
  },
  update: (id, topicData) => {
    return api.put(`topics/${id}`, topicData);
  },
  delete: (id) => {
    return api.delete(`topics/${id}`);
  },
  search: (keyword, page = 0, size = 10) => {
    return api.get(`topics/search?keyword=${keyword}&page=${page}&size=${size}`);
  },
  getByCategory: (category) => {
    return api.get(`topics/category/${category}`);
  }
};

// Post service
export const postService = {
  getByTopic: (topicId, page = 0, size = 20, sort = 'createdAt', direction = 'asc') => {
    return api.get(`posts/topic/${topicId}?page=${page}&size=${size}&sort=${sort}&direction=${direction}`);
  },
  getById: (id) => {
    return api.get(`posts/${id}`);
  },
  create: (topicId, postData) => {
    return api.post(`posts/topic/${topicId}`, postData);
  },
  update: (id, postData) => {
    return api.put(`posts/${id}`, postData);
  },
  delete: (id) => {
    return api.delete(`posts/${id}`);
  },
  search: (keyword, page = 0, size = 20) => {
    return api.get(`posts/search?keyword=${keyword}&page=${page}&size=${size}`);
  }
};

export default api;