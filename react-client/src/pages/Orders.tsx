import React, {useEffect, useState} from 'react';
import './../styles/App.css'
import {deleteOrder, fetchOrders} from "../api/OrderService";
import {Order} from "../types";
import {Link} from "react-router-dom";
import MyButton from "../components/UI/button/MyButton";
import MyInput from "../components/UI/input/MyInput";
import classes from "../styles/Orders.module.css"
import MyDelButton from "../components/UI/button/MyDelButton";

function Orders() {
    const [orders, setOrders] = useState<Order[]>([]);
    const [error, setError] = useState<Error>();
    const [isLoading, setIsLoading] = useState(true);
    const [queryParams, setQueryParams] = useState({minTotal: "", maxTotal: ""});
    let getOrders = async () => {
        try {
            setIsLoading(true);
            const data = await fetchOrders(queryParams);
            console.log(data)
            setOrders(data);
        } catch (err) {
            setError((err as Error));
        } finally {
            setIsLoading(false);
        }
    };
    useEffect(() => {
        getOrders();
    }, []);

    async function handleSubmit(event: React.FormEvent) {
        event.preventDefault();
        await getOrders();
    }

    async function deleteOrderById(id: number) {
        let answer = window.confirm(`Order with id ${id} will be deleted. Are you sure?`);
        if (answer.valueOf()) {
            const response = await deleteOrder(id)
            if (response.status != 200) {
                alert("Order wasn't deleted");
            }
            await getOrders();
        }
    }

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return (
            <div style={{color: "red"}}>
                <h2>Error</h2>
                <p>{error.message}</p>
                <p>Please check your server connection and try again later.</p>
            </div>
        );
    }

    return (
        <div className={classes.ordersContainer}>
            <form onSubmit={handleSubmit}>
                <div className={"input-group-container"}>
                    <MyInput
                        type="text"
                        placeholder={"Minimum Total"}
                        name={"minTotal"}
                        onChange={event => setQueryParams({...queryParams, minTotal: event.target.value})}
                        value={queryParams.minTotal}
                    />
                    <MyInput
                        name="maxTotal"
                        type="text"
                        placeholder={"Maximum Total"}
                        value={queryParams.maxTotal}
                        onChange={event => setQueryParams({...queryParams, maxTotal: event.target.value})}
                    />
                </div>
                <MyButton>Пошук</MyButton>
            </form>
            <h1>Orders</h1>
            {orders.length > 0 ? (
                <div className={classes.orderTableContainer}>
                    <table className={classes.orderTable}>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Buyer</th>
                            <th>Receiver</th>
                            <th>Status</th>
                            <th>Total Quantity</th>
                            <th>Total Amount</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {orders.sort((a, b) => a.id - b.id).map((order) => (
                            <tr key={order.id}>
                                <td><Link to={`/orders/${order.id}`}>{order.id}</Link></td>
                                <td>{`${order.buyer.lastName} ${order.buyer.firstName} ${order.buyer.middleName}`}</td>
                                <td>{`${order.receiver.lastName} ${order.receiver.firstName} ${order.receiver.middleName}`}</td>
                                <td>{order.status}</td>
                                <td>
                                    {Array.isArray(order.orderItems?.orderItem) &&
                                        order.orderItems.orderItem
                                            .map((item) => item.count.value)
                                            .reduce((sum, count) => sum + count, 0)}
                                </td>
                                <td>
                                    {Array.isArray(order.orderItems?.orderItem) &&
                                        order.orderItems.orderItem
                                            .map((item) => item.total.value)
                                            .reduce((sum, total) => sum + total, 0)
                                            .toFixed(2)}{" "}
                                    {Array.isArray(order.orderItems?.orderItem) && order.orderItems.orderItem.length > 0
                                        ? order.orderItems.orderItem[0].total.currency
                                        : ""}
                                </td>
                                <td>
                                    <MyDelButton name={'deleteBtn'}
                                              onClick={() => deleteOrderById(order.id)}>Видалити</MyDelButton>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

            ) : (
                <p>No orders found.</p>
            )}
        </div>
    );
}

export default Orders;
