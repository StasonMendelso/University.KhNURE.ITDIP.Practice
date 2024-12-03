import React, { useEffect, useState } from 'react';
import './../styles/App.css';
import { fetchOrderById } from "../api/OrderService";
import { Order } from "../types";
import {useNavigate, useParams} from "react-router-dom";
import MyButton from "../components/UI/button/MyButton";

function OrderId() {
    const params = useParams();
    const [order, setOrder] = useState<Order>();
    const [error, setError] = useState<Error>();
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const getOrder = async () => {
            try {
                setIsLoading(true);
                const id = Number(params.id);
                const data = await fetchOrderById(id);
                setOrder(data);
            } catch (err) {
                setError(err as Error);
            } finally {
                setIsLoading(false);
            }
        };

        getOrder();
    }, [params.id]);

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return (
            <div style={{ color: "red" }}>
                <h2>Error</h2>
                <p>{error.message}</p>
                <p>Please check your server connection and try again later.</p>
            </div>
        );
    }

    return (
        <div>
            {order ? (
                <>
                    <h1>Order №{order.id}</h1>
                    <MyButton onClick={()=>navigate(`/orders/${order.id}/update`)}>Редагувати</MyButton>
                    <div>
                        <h3>Buyer Information:</h3>
                        <p><strong>Name:</strong> {`${order.buyer.firstName} ${order.buyer.lastName} ${order.buyer.middleName}`}</p>
                        <p><strong>Email:</strong> {order.buyer.email}</p>
                        <p><strong>Phone:</strong> {order.buyer.telephoneNumber}</p>
                    </div>

                    <div>
                        <h3>Receiver Information:</h3>
                        <p><strong>Name:</strong> {`${order.receiver.firstName} ${order.receiver.lastName} ${order.receiver.middleName}`}</p>
                        <p><strong>Email:</strong> {order.receiver.email}</p>
                        <p><strong>Phone:</strong> {order.receiver.telephoneNumber}</p>
                    </div>

                    {order.delivery && (
                        <div>
                            <h3>Delivery Information:</h3>
                            <p><strong>Address:</strong> {order.delivery.address}</p>
                            <p><strong>Department:</strong> {order.delivery.departmentNumber}</p>
                            <p><strong>Service:</strong> {order.delivery.deliveryService}</p>
                            <p><strong>Status:</strong> {order.delivery.status}</p>
                        </div>
                    )}

                    <div>
                        <h3>Order Details:</h3>
                        <p><strong>Status:</strong> {order.status}</p>
                        <p><strong>Total Quantity:</strong> {order.orderItems?.orderItem.map((item) => item.count.value).reduce((sum, count) => sum + count, 0)}</p>
                        <p><strong>Total Amount:</strong> {order.orderItems?.orderItem
                            .map((item) => item.total.value)
                            .reduce((sum, total) => sum + total, 0)
                            .toFixed(2)} {order.orderItems?.orderItem[0]?.total.currency}</p>
                    </div>

                    <div>
                        <h3>Ordered Shoes:</h3>
                        {Array.isArray(order.orderItems?.orderItem) && order.orderItems.orderItem.map((item, index) => (
                            <div key={index} style={{ border: "1px solid #ddd", padding: "10px", marginBottom: "10px" }}>
                                <h4>{item.shoe.name}</h4>
                                <p><strong>Manufacturer:</strong> {item.shoe.manufacturer.name} {item.shoe.manufacturer.address}</p>
                                <p><strong>Size:</strong> {item.shoe.size}</p>
                                <p><strong>Model:</strong> {item.shoe.model}</p>
                                <p><strong>Article:</strong> {item.shoe.article}</p>
                                <p><strong>Material:</strong> {item.shoe.productMaterial}</p>
                                <p><strong>Price:</strong> {item.shoe.price.value} {item.shoe.price.currency}</p>
                                <p><strong>Quantity:</strong> {item.count.value}</p>
                                {item.discount && (
                                    <p><strong>Discount:</strong> {item.discount.value} {item.discount.currency}</p>
                                )}
                                <p><strong>Total:</strong> {item.total.value} {item.total.currency}</p>
                            </div>
                        ))}
                    </div>
                </>
            ) : (
                <p>No orders found.</p>
            )}
        </div>
    );
}

export default OrderId;