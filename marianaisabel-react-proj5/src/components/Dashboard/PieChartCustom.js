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

function PieChartCustom ({data,header,...props}){
    
console.log(data)
const intl = useIntl();



return ( 
    <Box >
          <Heading>{intl.formatMessage({id: header})}</Heading>
        <Box style={{ textAlign: 'center'}}>
            <PieChart width={800} height={500} data={data} />
        </Box>
    </Box>

);}
export default PieChartCustom;
