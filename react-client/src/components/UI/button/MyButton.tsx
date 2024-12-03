import React from 'react';
import classes from './MyButton.module.css'

export interface MyButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
}

const MyButton: React.FC<MyButtonProps> = ({children, ...props}) => {
    return (
        <button {...props} className={classes.myBtn + " " + props.className}>
            {children}
        </button>
    );
};

export default MyButton;