import React from 'react';
import classes from './MyInput.module.css'

interface MyInputProps extends React.InputHTMLAttributes<HTMLInputElement> {}
const MyInput: React.FC<MyInputProps> = (props) => {
    return <input {...props} className={classes.myInput}/>;
};

export default MyInput;