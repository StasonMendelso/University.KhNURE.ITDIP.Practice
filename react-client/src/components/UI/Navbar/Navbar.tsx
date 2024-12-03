import {Link} from "react-router-dom";

const Navbar = () => {

    return (
        <div className="navbar">
            <div className="navbar_links">
                <Link to="/orders">Замовлення</Link>
                <Link to="/orders/create">Додати замовлення</Link>
            </div>
        </div>
    );
};

export default Navbar;