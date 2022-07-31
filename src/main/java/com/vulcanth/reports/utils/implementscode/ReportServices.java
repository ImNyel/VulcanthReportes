package com.vulcanth.reports.utils.implementscode;

import com.vulcanth.nyel.other.model.Model;
import com.vulcanth.reports.utils.ReportsUtil;

import java.util.Set;

public interface ReportServices extends Model<String, ReportsUtil> {

    Set<ReportsUtil> getReports();

}
