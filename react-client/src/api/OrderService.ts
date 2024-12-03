import axios, {AxiosResponse} from "axios";
import {Order} from "../types";

const API_URL = "http://localhost:8091/servlet-app/orders";

export const fetchOrders = async (queryParams: { minTotal: string, maxTotal: string }): Promise<Order[]> => {
    let config = {};
    if (queryParams.minTotal != null && queryParams.minTotal != ""
        && queryParams.maxTotal != "" && queryParams.maxTotal != null) {
        config = {params: queryParams}
    }
    const response = await axios.get(API_URL, config);
    return response.data;
};

export const fetchOrderById = async (id: number): Promise<Order> => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
};

export const createOrder = async (order: Partial<Order>): Promise<AxiosResponse> => {
    return await axios.post(API_URL, order, {
        validateStatus: null,
        maxRedirects: 0
    });
};

export const updateOrder = async (
    id: number,
    order: Partial<Order>
): Promise<AxiosResponse> => {
    return await axios.put(`${API_URL}/${id}`, order);
};

export const deleteOrder = async (id: number): Promise<AxiosResponse> => {
    return await axios.delete(`${API_URL}/${id}`);
};