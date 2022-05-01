import React from 'react';
import { PieChart } from 'reaviz';
import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    Box,

} from "@chakra-ui/react";

import {FormattedMessage ,useIntl} from "react-intl";

function PieChartCustom ({data,...props}){
    
console.log(data)
const intl = useIntl();



return ( 
    <Box >
       
        <Box style={{ textAlign: 'center'}}>
            <PieChart  width={300} height={200}data={data} />
        </Box>
    </Box>

);}
export default PieChartCustom;
