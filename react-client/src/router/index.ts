import Orders from "../pages/Orders";
import OrderId from "../pages/OrderId";
import CreateOrder from "../pages/CreateOrder";
import UpdateOrder from "../pages/UpdateOrder";

export const routes = [
    {path: '/orders', component: Orders},
    {path: '/orders/:id', component: OrderId},
    {path: '/orders/create', component: CreateOrder},
    {path: '/orders/:id/update', component: UpdateOrder},
]