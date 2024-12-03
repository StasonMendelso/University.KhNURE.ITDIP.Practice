import React from 'react';
import classes from './MySelect.module.css'
interface Option {
    value: string;
    name: string;
}

interface MySelectProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
    options: Option[];
}

const MySelect: React.FC<MySelectProps> = ({ options, defaultValue, value, onChange, ...rest }) => {
    // Handle onChange correctly
    const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        // Extract value from the event and pass to the custom onChange function
        if (onChange) {
            onChange(event);
        }
    };

    return (
        <select value={value} onChange={handleChange} {...rest} className={classes.mySelect}>
            {options.map(opt => (
                <option key={opt.value} value={opt.value}>
                    {opt.name}
                </option>
            ))}
        </select>
    );
};

export default MySelect;
