import React, {useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {fetchOrderById, updateOrder} from "../api/OrderService";
import {createOrderItem} from "../types";
import MyInput from "../components/UI/input/MyInput";
import MySelect from "../components/UI/select/MySelect";
import MyButton from "../components/UI/button/MyButton";
import MyDelButton from "../components/UI/button/MyDelButton";

const UpdateOrder: React.FC = () => {
    const {id} = useParams<{ id: string }>(); // Get order ID from route parameters
    const navigate = useNavigate();

    const [buyer, setBuyer] = useState({
        firstName: "",
        lastName: "",
        middleName: "",
        email: "",
        telephoneNumber: "",
    });

    const [receiver, setReceiver] = useState({
        firstName: "",
        lastName: "",
        middleName: "",
        email: "",
        telephoneNumber: "",
    });

    const [delivery, setDelivery] = useState({
        id: 0,
        address: "",
        departmentNumber: "",
        deliveryService: "",
        status: "",
    });

    const [orderItems, setOrderItems] = useState([
        {
            ...createOrderItem()
        },
    ]);

    const [status, setStatus] = useState("");
    const [orderId, setOrderId] = useState(0);
    // Fetch the current order details
    React.useEffect(() => {
        const fetchOrder = async () => {
            try {
                const order = await fetchOrderById(Number(id));
                setOrderId(order.id);
                setBuyer(order.buyer);
                setReceiver(order.receiver);
                setDelivery(order.delivery || {
                    id: 0,
                    address: "",
                    departmentNumber: "",
                    deliveryService: "",
                    status: "",
                });
                setOrderItems(order.orderItems.orderItem || []);
                setStatus(order.status);
            } catch (error) {
                console.error("Failed to fetch order:", error);
                alert("Error loading order details.");
            }
        };

        if (id) fetchOrder();
    }, [id]);

    const handleInputChange = (
        event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
        section: string,
        index?: number,
        subfield?: string,
        subfield2?: string
    ) => {
        const { name, value } = event.target;
        if (section === "buyer") {
            setBuyer({...buyer, [name]: value});
        } else if (section === "receiver") {
            setReceiver({...receiver, [name]: value});
        }else if (section === "delivery") {
            setDelivery({...delivery, [name]: value});
        } else if (section === "orderItems" && index !== undefined) {
            const updatedOrderItems = [...orderItems];
            if (subfield && subfield2) {
                let element = (((updatedOrderItems[index][subfield as keyof typeof updatedOrderItems[typeof index]] as any)[subfield2]) as any)[name];
                const convertedValue = convertType(value, typeof element);
                updatedOrderItems[index] = {
                    ...updatedOrderItems[index],
                    [subfield]: {
                        ...(updatedOrderItems[index][subfield as keyof typeof updatedOrderItems[typeof index]] as any),
                        [subfield2]: {
                            ...((updatedOrderItems[index][subfield as keyof typeof updatedOrderItems[typeof index]] as any)[subfield2]),
                            [name]: convertedValue,
                        },
                    },
                };
            } else if (subfield) {
                let updatedOrderItemElement = updatedOrderItems[index][subfield as keyof typeof updatedOrderItems[typeof index]];
                if (updatedOrderItemElement == null && subfield === "discount"){
                    updatedOrderItems[index] = {
                        ...updatedOrderItems[index],
                        "discount": {
                            "value": 0,
                            "currency": "UAH"
                        },
                    };
                }
                updatedOrderItemElement = updatedOrderItems[index][subfield as keyof typeof updatedOrderItems[typeof index]];
                let element = (updatedOrderItemElement as any)[name];
                const convertedValue = convertType(value, typeof element);
                updatedOrderItems[index] = {
                    ...updatedOrderItems[index],
                    [subfield]: {
                        ...(updatedOrderItems[index][subfield as keyof typeof updatedOrderItems[typeof index]] as any),
                        [name]: convertedValue,
                    },
                };
            } else {
                let element = (updatedOrderItems[index] as any)[name];
                const convertedValue = convertType(value, typeof element);
                updatedOrderItems[index] = {
                    ...updatedOrderItems[index],
                    [name]: convertedValue,
                };
            }
            setOrderItems(updatedOrderItems);
        }
    };

    const convertType = (value: string, targetType: string): any => {
        switch (targetType) {
            case 'number':
                return parseFloat(value);
            case 'boolean':
                return value === 'true';
            default:
                return value;
        }
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const updatedOrder = {
            buyer,
            receiver,
            delivery,
            orderItems: {orderItem: orderItems},
            status,
        };

        try {
            const response = await updateOrder(Number(id), updatedOrder);

            if (response.status === 200) {
                alert("Order updated successfully!");
                navigate(`/orders/${id}`);
            } else if (response.status === 400) {
                alert("Invalid values entered. Please correct them.");
            } else {
                alert("An unknown error occurred. Please try again later.");
            }
        } catch (error) {
            console.error("Error updating order:", error);
            alert("Failed to update order. Please try again.");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Update Order</h2>
            <MyButton onClick={() => navigate(`/orders/${orderId}`)}>Назад</MyButton>
            <div>
                <MySelect
                    name="status"
                    value={status}
                    onChange={(e) => setStatus(e.target.value)}
                    options={[
                        {value: "Очікує розгляд",name:"Очікує розгляд"},
                        {value: "Підтверджено",name:"Підтверджено"},
                        {value: "Відхилено",name:"Відхилено"},
                        {value: "Скасовано",name:"Скасовано"},
                        {value: "Виконано",name:"Виконано"},
                        {value: "В обробці",name:"В обробці"},
                        {value: "На доставці",name:"На доставці"},
                    ]}
                />
                <h3>Buyer Information</h3>
                <div className={"input-group-container"}>
                    <MyInput
                        type="text"
                        name="firstName"
                        placeholder="First Name"
                        value={buyer.firstName}
                        onChange={(e) => handleInputChange(e, "buyer")}
                    />
                    <MyInput
                        type="text"
                        name="lastName"
                        placeholder="Last Name"
                        value={buyer.lastName}
                        onChange={(e) => handleInputChange(e, "buyer")}
                    />
                    <MyInput
                        type="text"
                        name="middleName"
                        placeholder="Middle Name"
                        value={buyer.middleName}
                        onChange={(e) => handleInputChange(e, "buyer")}
                    />
                    <MyInput
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={buyer.email}
                        onChange={(e) => handleInputChange(e, "buyer")}
                    />
                    <MyInput
                        type="text"
                        name="telephoneNumber"
                        placeholder="Telephone Number"
                        value={buyer.telephoneNumber}
                        onChange={(e) => handleInputChange(e, "buyer")}
                    />
                </div>


                <h3>Receiver Information</h3>
                <div className={"input-group-container"}>
                    <MyInput
                        type="text"
                        name="firstName"
                        placeholder="First Name"
                        value={receiver.firstName}
                        onChange={(e) => handleInputChange(e, "receiver")}
                    />
                    <MyInput
                        type="text"
                        name="lastName"
                        placeholder="Last Name"
                        value={receiver.lastName}
                        onChange={(e) => handleInputChange(e, "receiver")}
                    />
                    <MyInput
                        type="text"
                        name="middleName"
                        placeholder="Middle Name"
                        value={receiver.middleName}
                        onChange={(e) => handleInputChange(e, "receiver")}
                    />
                    <MyInput
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={receiver.email}
                        onChange={(e) => handleInputChange(e, "receiver")}
                    />
                    <MyInput
                        type="text"
                        name="telephoneNumber"
                        placeholder="Telephone Number"
                        value={receiver.telephoneNumber}
                        onChange={(e) => handleInputChange(e, "receiver")}
                    />
                </div>

                <h3>Delivery Information</h3>
                <div className={"input-group-container"}>
                    <MyInput
                        type="text"
                        name="address"
                        placeholder="Delivery Address"
                        value={delivery.address}
                        onChange={(e) => handleInputChange(e, "delivery")}
                    />
                    <MyInput
                        type="text"
                        name="departmentNumber"
                        placeholder="Department Number"
                        value={delivery.departmentNumber}
                        onChange={(e) => handleInputChange(e, "delivery")}
                    />
                    <MyInput
                        type="text"
                        name="deliveryService"
                        placeholder="Delivery Service"
                        value={delivery.deliveryService}
                        onChange={(e) => handleInputChange(e, "delivery")}
                    />
                    <MySelect
                        name="status"
                        value={delivery.status}
                        onChange={(e) => handleInputChange(e, "delivery")}
                        options={[
                            {value: '', name: ''},
                            {value: 'Пакується', name: 'Пакується'},
                            {value: 'Доставляється', name: 'Доставляється'},
                            {value: 'Доставлено', name: 'Доставлено'},
                            {value: 'Повернуто на склад', name: 'Повернуто на склад'},
                            {value: 'Повертається', name: 'Повертається'},
                        ]}
                    />
                </div>


                <h3>Order Items</h3>
                {orderItems.map((item, index) => (
                    <div key={index}>
                        <h4>Item {index + 1}</h4>
                        <div className={"input-group-container"}>
                            <MyInput
                                type="text"
                                name="name"
                                placeholder="Shoe Name"
                                value={item.shoe.name}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe")}
                            />
                            <MyInput
                                type="text"
                                name="name"
                                placeholder="Manufacturer Name"
                                value={item.shoe.manufacturer.name}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe", "manufacturer")}
                            />
                            <MyInput
                                type="text"
                                name="address"
                                placeholder="Manufacturer Address"
                                value={item.shoe.manufacturer.address}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe", "manufacturer")}
                            />
                            <MyInput
                                type="text"
                                name="size"
                                placeholder="Size"
                                value={item.shoe.size ? item.shoe.size : ""}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe")}
                            />
                            <MyInput
                                type="text"
                                name="productMaterial"
                                placeholder="Product Material"
                                value={item.shoe.productMaterial}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe")}
                            />
                            <MyInput
                                type="text"
                                name="model"
                                placeholder="Model"
                                value={item.shoe.model}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe")}
                            />
                            <MyInput
                                type="text"
                                name="article"
                                placeholder="Article"
                                value={item.shoe.article}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe")}
                            />
                            <MyInput
                                type="text"
                                name="value"
                                placeholder="Price"
                                value={item.shoe.price.value ? item.shoe.price.value : ""}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "shoe","price")}
                            />
                            <MyInput
                                type="text"
                                name="value"
                                placeholder="Quantity"
                                value={item.count.value ? item.count.value : ""}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "count")}
                            />
                            <MyInput
                                type="text"
                                name="value"
                                placeholder="Discount"
                                value={item.discount?.value ? item.discount.value : ""}
                                onChange={(e) => handleInputChange(e, "orderItems", index, "discount")}
                            />
                        </div>

                        <MyDelButton
                            type="button"
                            onClick={() => setOrderItems(orderItems.filter((_, index1) => index1 !== index))}
                        >
                            Delete order item
                        </MyDelButton>
                    </div>
                ))}
                <MyButton
                    style={{"margin":"10px 0"}}
                    type="button"
                    onClick={() =>
                        setOrderItems([
                            ...orderItems,
                            createOrderItem()
                        ])
                    }
                >
                    Add order item
                </MyButton>
            </div>


            <MyButton type="submit">Update Order</MyButton>
        </form>
    );
};

export default UpdateOrder;
