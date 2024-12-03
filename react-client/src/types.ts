export interface Manufacturer {
    id?: number;
    name: string;
    address: string;
}

export interface Shoe {
    id?: number;
    manufacturer: Manufacturer;
    name: string;
    size: number;
    productMaterial: string;
    model: string;
    article: string;
    price: {
        value: number;
        currency: string;
    };
}

export interface OrderItem {
    id?: number;
    shoe: Shoe;
    count: {
        value: number;
    };
    discount?: {
        value: number;
        currency: string;
    };
    total: {
        value: number;
        currency: string;
    };
}

export function createOrderItem(): OrderItem {
    return {
        total: {
            value: 0,
            currency: "UAH"
        },
        discount:{
            value: 0,
            currency: "UAH"
        },
        count: {
            value: 0
        },
        shoe:{
            id: 0,
            manufacturer:{
                id: 0,
                name: "",
                address: ""
            },
            name: "",
            article: "",
            model: "",
            price: {
                value: 0,
                currency: "UAH"
            },
            size: 0,
            productMaterial: ""
        },
        id: 0
    } as OrderItem;
}

export interface Buyer {
    id?: number;
    email: string;
    lastName: string;
    firstName: string;
    middleName: string;
    telephoneNumber: string;
}

export interface Receiver {
    id?: number;
    email: string;
    lastName: string;
    firstName: string;
    middleName: string;
    telephoneNumber: string;
}

export interface Delivery {
    id: number;
    address: string;
    departmentNumber: string;
    deliveryService: string;
    status: string;
}


export interface Order {
    id: number;
    orderItems: { orderItem: OrderItem[] };
    buyer: Buyer;
    receiver: Receiver;
    delivery?: Delivery;
    status: string;
}
