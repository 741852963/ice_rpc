
#pragma once

module Demo
{

interface Hello
{
    idempotent void sayHello(int delay);
    void shutdown();
};

};
