enum HttpMethod {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE',
}

interface RequestOptions {
  method: HttpMethod;
  url: string;
  data?: Record<string, any>;
}

const send = ({ method, url, data }: RequestOptions) => {
  const headers = new Headers(
    data ? [['Content-Type', 'application/json']] : []
  );

  const requestData: RequestInit = {
    headers: headers,
    method: method,
    body: data ? JSON.stringify(data) : null,
  };

  return fetch(new URL(`${url}`), requestData)
    .then(async (response) => {
      return await response.json();
    })
    .catch((err) => {
      throw new Error(err);
    });
};

export const get = (url: string) => {
  return send({ method: HttpMethod.GET, url });
};

export const post = (url: string, data?: {}) => {
  return send({ method: HttpMethod.POST, url, data });
};

export const put = (url: string, data?: {}) => {
  return send({ method: HttpMethod.PUT, url, data });
};

export const del = (url: string) => {
  return send({ method: HttpMethod.DELETE, url });
};
