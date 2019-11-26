package com.quiet.live.hall.auto;



import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.quiet.live.hall.utils.base.MD5Util;
import com.quiet.live.hall.utils.string.StringUtil;

public class MybatisPlusGenerator {

private static MybatisPlusGenerator single = null;
	
	private MybatisPlusGenerator() {
		super();
	}
	
	@SuppressWarnings("unused")
	private static MybatisPlusGenerator getSingle() {
		if(single == null) {
			single =new MybatisPlusGenerator();
		}
		return single;
	}
	
	public void autoGeneration() {
		 GlobalConfig config = new GlobalConfig();
	        String dbUrl = "jdbc:mysql://47.112.109.175:3306/jxshg?serverTimezone=GMT%2B8";
	        DataSourceConfig dataSourceConfig = new DataSourceConfig();
	        dataSourceConfig.setDbType(DbType.MYSQL)
	                .setUrl(dbUrl)
	                .setUsername("root")
	                .setPassword("456123@zhongrl")
	                .setDriverName("com.mysql.cj.jdbc.Driver");
	        StrategyConfig strategyConfig = new StrategyConfig();
	        strategyConfig
	                .setCapitalMode(true)
	                .setEntityLombokModel(false)
	                .setDbColumnUnderline(true)
	                .setNaming(NamingStrategy.underline_to_camel);
	        config.setActiveRecord(false)
	                .setEnableCache(false)
	                .setAuthor("william zhong")
	                //指定输出文件夹位置
	                .setOutputDir("E:\\workspace_jx\\quiet-live-hall\\src\\main\\java\\")
	                .setFileOverride(true)
	                .setServiceName("%sService");
	        new AutoGenerator().setGlobalConfig(config)
	                .setDataSource(dataSourceConfig)
	                .setStrategy(strategyConfig)
	                .setPackageInfo(
	                        new PackageConfig()
	                                .setParent("com.quiet.live.hall")
	                                .setController("rest")
	                                .setEntity("entity")
	                ).execute();
	}
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		MybatisPlusGenerator generator = MybatisPlusGenerator.getSingle();
//		generator.autoGeneration();
		System.out.println(StringUtil.getUUID());
		System.out.println(MD5Util.md5("111111"));
	}
}
