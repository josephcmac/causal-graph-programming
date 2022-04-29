class processTest extends org.scalatest.funsuite.AnyFunSuite {


    test("helpers.helpers.findDir") {
        assert( helpers.helpers.findDir("/home/caballero/GitHub/linux/") === "linux/" )
    }

    test("helpers.helpers.clarifyAddress") {
        assert( helpers.helpers.clarifyAddress("/home/caballero/GitHub/linux/", "linux/",
        "/home/caballero/GitHub/linux/drivers/atm/", 
        "/home/caballero/GitHub/linux/drivers/atm/ambassador.c", 
        "\"ambassador.h\"") === "/home/caballero/GitHub/linux/drivers/atm/ambassador.h" )

        assert( helpers.helpers.clarifyAddress("/home/caballero/GitHub/linux/", "linux/",
        "/home/caballero/GitHub/linux/",
        "/home/caballero/GitHub/linux/drivers/atm/ambassador.c",
        "<linux/module.h>") === "/home/caballero/GitHub/linux/module.h" )

        assert( helpers.helpers.clarifyAddress("/home/caballero/GitHub/linux/", "linux/",
        "/home/caballero/GitHub/linux/drivers/atm/",
        "/home/caballero/GitHub/linux/drivers/atm/ambassador.c", 
        "<asm/io.h>") === "/home/caballero/GitHub/linux/include/asm-generic/io.h" )

        assert( helpers.helpers.clarifyAddress("/home/caballero/GitHub/linux/", 
        "linux/",
        "/home/caballero/GitHub/linux/drivers/pinctrl/samsung/",
        "samsung/", 
        "<dt-bindings/pinctrl/samsung.h>") === "/home/caballero/GitHub/linux/include/dt-bindings/pinctrl/samsung.h" )

        assert( helpers.helpers.clarifyAddress("/home/caballero/GitHub/linux/", 
        "linux/",
        "/home/caballero/GitHub/linux/drivers/pinctrl/samsung/",
        "samsung/", 
        "\"../core.h\"") === "/home/caballero/GitHub/linux/drivers/pinctrl/core.h")

        assert( helpers.helpers.clarifyAddress("/home/caballero/GitHub/linux/", 
        "linux/",
        "/home/caballero/GitHub/linux/drivers/power/supply/",
        "supply/", 
        "\"../../w1/slaves/w1_ds2781.h\"") === "/home/caballero/GitHub/linux/drivers/w1/slaves/w1_ds2781.h")

        assert( helpers.helpers.clarifyAddress("/home/caballero/GitHub/linux/", 
        "linux/",
        "/home/caballero/GitHub/linux/drivers/block/mtip32xx/",
        "mtip32xx/", 
        "<../drivers/ata/ahci.h>") === "/home/caballero/GitHub/linux/drivers/ata/ahci.h")



    }

}