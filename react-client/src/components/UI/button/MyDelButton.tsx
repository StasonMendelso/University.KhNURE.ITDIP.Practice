import React from 'react';
import PropTypes from 'prop-types';
import MyButton, {MyButtonProps} from "./MyButton";
import classes from "./MyButton.module.css"

const MyDelButton: React.FC<MyButtonProps> = ({children, ...props}) => {
    return (
       <MyButton {...props} className={classes.myDelButton}>{children}</MyButton>
    );
};

MyDelButton.propTypes = {

};

export default MyDelButton;