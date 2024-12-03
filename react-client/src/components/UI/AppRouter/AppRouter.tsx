import {Navigate, Route, Routes} from 'react-router-dom';
import {routes} from "../../../router";
import classes from './AppRouter.module.css';

const AppRouter = () => {
    return (
        <div className={classes.routerContainer}>
            <Routes>
                {routes.map((route) => (
                    <Route path={route.path} element={<route.component/>} key={route.path}/>
                ))}
                <Route
                    path="*"
                    element={<Navigate to="/orders"/>}
                /> {/* Redirect for invalid routes */}
            </Routes>
        </div>

    );
};

export default AppRouter;